package pl.olafcio.avoid_platform.loader_impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.olafcio.avoid.platform.RunningEnv;
import pl.olafcio.avoid.util.ModEnvironment;
import pl.olafcio.avoid_loader.AvoidModContainer;
import pl.olafcio.avoid_loader.PreModLoader;
import pl.olafcio.avoid_loader.abst.ModFile;
import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.IModFile;
import pl.olafcio.avoid_loader.interfaces.services.IModLoader;
import pl.olafcio.avoid_loader.interfaces.services.standard.IAvoidModDescriber;
import pl.olafcio.avoid_loader.interfaces.standard.IAvoidModMetadata;
import pl.olafcio.avoid_platform.AvoidWrappedLoader;
import pl.olafcio.avoid_platform.DigestUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class AvoidModLoader implements IModLoader {
    private static final Logger LOGGER
                       = LoggerFactory.getLogger("AvoidLoader/Mod Loader");

    @Override
    public Collection<IMod> process(IModFile iModFile) {
        if (iModFile instanceof ModFile modFile) {
            Path mod = modFile.getPath();
            AvoidModContainer container;

            try {
                IAvoidModMetadata manifest = null;

                var describers = PreModLoader.getInstance().loadService(IAvoidModDescriber.class);

                for (var describer : describers) {
                    var metadata = describer.describe(modFile);
                    if (metadata != null)
                        manifest = metadata;
                }

                if (manifest == null)
                    return List.of();

                var env = manifest.env();
                if (env == ModEnvironment.CLIENT) {
                    if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT) {
                        LOGGER.warn("Skipping client-only mod: {} [{}]", manifest.name(), manifest.id());
                        return null;
                    }
                } else if (env == ModEnvironment.SERVER) {
                    if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.SERVER) {
                        LOGGER.warn("Skipping server-only mod: {} [{}]", manifest.name(), manifest.id());
                        return null;
                    }
                }

                container = new AvoidModContainer(modFile, this, manifest, iModFile instanceof JIJModFile jij ? jij.parent : null);
            } catch (IOException e) {
                throw new RuntimeException("Error while parsing JAR file '%s'".formatted(mod.toAbsolutePath()), e);
            }

            try {
                extractJIJ(container);
            } catch (IOException e) {
                throw new RuntimeException("Error while resolving JIJ of JAR file '%s'".formatted(mod.toAbsolutePath()), e);
            }

            return List.of(container);
        }

        return List.of();
    }

    private void extractJIJ(AvoidModContainer container) throws IOException {
        if (container.metadata().jars() != null) {
            var jars = container.metadata().jars();
            var folder = Path.of(".cache/avoid_loader/embedded_jars");

            try                   { Files.createDirectories(folder);                                                      }
            catch (IOException e) { throw new RuntimeException("Failed to create Avoid embedded jar cache directory", e); }

            for (var embeddedJAR : jars) {
                var jarPath = embeddedJAR.path();

                Path path;

                try (var stream = container.getModFile().getResourceAsStream(jarPath)) {
                    path = folder.resolve(DigestUtils.sha1Hex(stream) + ".jar");
                }

                try (var fos = new FileOutputStream(path.toFile())) {
                    try (var stream = container.getModFile().getResourceAsStream(jarPath)) {
                        stream.transferTo(fos);
                    }
                }

                boolean mod     = Objects.requireNonNullElse(embeddedJAR.mod(), true);
                boolean library = Objects.requireNonNullElse(embeddedJAR.library(), true);

                if (mod) {
                    if (library) {
                        recall(new JIJModFile(path, container.classLoader(), container));

                        container.classLoader().addURL(path.toUri().toURL());
                    } else {
                        recall(new JIJModFile(path, container));
                    }
                } else {
                    if (!library)
                        throw new RuntimeException("Jar-in-jar entry with library:false & mod:false; cannot load into void");

                    container.classLoader().addURL(path.toUri().toURL());
                }
            }
        }
    }
}
