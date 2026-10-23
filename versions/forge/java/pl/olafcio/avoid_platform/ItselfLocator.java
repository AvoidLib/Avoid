package pl.olafcio.avoid_platform;

import cpw.mods.jarhandling.JarMetadata;
import cpw.mods.jarhandling.SecureJar;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.ModuleLayerHandler;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import net.minecraftforge.fml.loading.LogMarkers;
import net.minecraftforge.fml.loading.moddiscovery.*;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.forgespi.locating.*;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.module.InvalidModuleDescriptorException;
import java.lang.module.ModuleDescriptor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ItselfLocator extends AbstractModProvider implements IModLocator {
    @Override
    public List<ModFileOrException> scanMods() {
        try {
            Path path = Path.of(".cache/avoid_loader/extract/Avoid JIJ.jar");

            Files.createDirectories(path.getParent());

            try (var stream = this.getClass().getResourceAsStream("/mod.jar")) {
                // Great. Mod extracting at runtime.
                // Now people are gonna ask "iS tHiS a ViRuS?!!!"
                // And not a stupid thought, 90% mods that do that are weedhacks.
                // Lex, i beg you, please make this not necessary
                //
                //  (i tried:
                //     > var path = Path.of(this.getClass().getResource("/mod.jar").toURI());
                //   but it didn't work, as then securejar fuckeverted it back to the parent jar.)
                //
                // I suppose the new ModuleLoader may fix these issues.

                try (var fos = new FileOutputStream(path.toFile())) {
                    stream.transferTo(fos);
                }
            }

            return List.of(Objects.requireNonNull(createMod(path, false, IModFile.Type.MOD.name()), "Avoid nested mod (with createMod(...)) is null"));
        } catch (IOException e) {
            var ex = new ModFileLoadingException("IOException when loading itself: " + e.getMessage());

            ex.setStackTrace(e.getStackTrace());

            return List.of(new ModFileOrException(null, ex));
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger("Avoid/SelfLocator");

    protected static JarMetadata loadMetaFromJar(SecureJar jar, ItJarMetadata mjm) {
        var info = jar.moduleDataProvider().open(MODULE_INFO).orElse(null);
        if (info != null) {
            try {
                var desc = ModuleDescriptor.read(info, jar::getPackages);
                var all = new HashSet<>(jar.getPackages());
                all.removeAll(desc.packages());
                if (!all.isEmpty()) {
                    var missing = all.stream().sorted().collect(Collectors.joining(", "));
                    LOGGER.error("Invalid module-info, missing packages " + missing);
                    throw new ModFileLoadingException("Invalid module-info, missing packages " + missing);
                } else {
                    return new JarMetadata() {
                        @Override
                        public String name() {
                            return desc.name();
                        }

                        @Override
                        public String version() {
                            return desc.version().map(ModuleDescriptor.Version::toString).or(desc::rawVersion).orElse(null);
                        }

                        @Override
                        public ModuleDescriptor descriptor() {
                            return desc;
                        }
                    };
                }
            } catch (InvalidModuleDescriptorException | IOException e) {
                LOGGER.error("Failed to parse " + jar.getPrimaryPath() + " module-info", e);
                throw new ModFileLoadingException("Invalid module-info: " + e.getMessage());
            } finally {
                try {
                    info.close();
                } catch (IOException e) {}
            }
        }

        if (jar.moduleDataProvider().findFile(MODS_TOML).isEmpty())
            return JarMetadata.from(jar, jar.getPrimaryPath());

        return mjm;
    }

    static final Attributes.Name TYPE = new Attributes.Name("FMLModType");

    @Nullable
    protected IModLocator.ModFileOrException createMod(Path path, boolean ignoreUnknown, String defaultType) {
        var debug = path.toString();
        if (debug.equals("/"))
            debug = path.toUri().toString();

        var meta = new ItJarMetadata();

        final SecureJar sj;

        try {
            sj = SecureJar.from(jar -> loadMetaFromJar(jar, meta), path);
        } catch (Throwable t) {
            return new IModLocator.ModFileOrException(null, new ModFileLoadingException("Failed to create secure jar for \"" + debug + "\" - " + t.getMessage()));
        }

        IModFile mod;
        var type = sj.moduleDataProvider().getManifest().getMainAttributes().getValue(TYPE);
        if (type == null)
            type = defaultType;

        if (sj.moduleDataProvider().findFile(MODS_TOML).isPresent()) {
            LOGGER.debug(LogMarkers.SCAN, "Found {} mod of type {}: {}", MODS_TOML, type, debug);

            mod = new ModFile(sj, this, ModFileParser::modsTomlParser);

            // ModJarMetadata is only used when loading mods.toml mods as ModJarMetadata
            meta.setModFile(mod);

            if (mod.getModFileInfo().getFileProperties().containsKey(ModFileInfo.NOT_A_FORGE_MOD_PROP)) {
                LOGGER.error(LogMarkers.SCAN, "Unable to load file \"{}\" because its mods.toml is requesting an invalid javafml loaderVersion (use \"*\" if you want to allow all versions) and is missing a forge modId dependency declaration (see the sample mods.toml in the MDK).", path);
                return new IModLocator.ModFileOrException(null, new ModFileLoadingException("File \"%s\" is not a Forge mod and cannot be loaded. Look for a Forge version of this mod or consider alternative mods.".formatted(mod.getFileName())));
            }
        } else if (type != null) {
            LOGGER.debug(LogMarkers.SCAN, "Found {} mod of type {}: {}", JarFile.MANIFEST_NAME, type, debug);

            mod = new ModFile(sj, this, this::manifestParser, type);
        } else if (ignoreUnknown) {
            return null;
        } else
            return new IModLocator.ModFileOrException(null, new ModFileLoadingException("Invalid mod file found " + debug));

        return new IModLocator.ModFileOrException(mod, null);
    }

    @Override
    public String name() {
        return "avoid-locator";
    }
}
