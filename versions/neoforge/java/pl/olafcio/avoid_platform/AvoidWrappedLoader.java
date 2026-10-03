package pl.olafcio.avoid_platform;

import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.moddiscovery.ModFile;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.neoforgespi.language.IModInfo;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.util.RunningEnv;
import pl.olafcio.avoid.mods.ModEnvironment;
import pl.olafcio.avoid.platform.mod.PlatformContact;
import pl.olafcio.avoid.platform.mod.PlatformContributor;
import pl.olafcio.avoid.platform.mod.PlatformDependency;
import pl.olafcio.avoid.platform.mod.PlatformMod;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@ApiStatus.Experimental
public final class AvoidWrappedLoader {
    @ApiStatus.Internal
    private AvoidWrappedLoader() {}

    public static boolean isFabric() { return false; }
    public static boolean isNeoForge() { return true; }
    public static boolean isSponge() { return false; }
    public static boolean isPaper() { return false; }

    public static Path getGameDir() {
        return FMLLoader.getCurrent().getGameDir();
    }

    public static boolean isModPresent(String id) {
        return FMLLoader.getCurrent().getLoadingModList().getModFileById(id) != null;
    }

    public static List<PlatformMod> getMods() {
        return FMLLoader.getCurrent().getLoadingModList().getModFiles().stream().flatMap(mod -> mod.getMods().stream().map(meta -> {
            var dependencies = new ArrayList<PlatformDependency>();

            for (var dependency : meta.getDependencies())
                dependencies.add(new PlatformDependency(
                        dependency.getModId(),
                        dependency.getType() == IModInfo.DependencyType.REQUIRED     ? PlatformDependency.Hard.REQUIRES     :
                        dependency.getType() == IModInfo.DependencyType.INCOMPATIBLE ? PlatformDependency.Hard.INCOMPATIBLE :
                        dependency.getType() == IModInfo.DependencyType.OPTIONAL     ? PlatformDependency.Hard.RECOMMENDS   :
                        dependency.getType() == IModInfo.DependencyType.DISCOURAGED  ? PlatformDependency.Soft.INCOMPATIBLE :
                                                                                       null,
                        dependency.getOrdering() == IModInfo.Ordering.BEFORE ? PlatformDependency.LoadOrder.BEFORE_MOD :
                        dependency.getOrdering() == IModInfo.Ordering.AFTER  ? PlatformDependency.LoadOrder.AFTER_MOD  :
                                                                               PlatformDependency.LoadOrder.UNDEFINED
                ));

            return new PlatformMod(
                    meta.getModId(),
                    meta.getDisplayName(),
                    meta.getDescription(),
                    meta.getConfig().<String>getConfigElement("authors")
                                    .map(authors -> Arrays.stream(authors.replace("&", ",")
                                                                               .replace(";", ",")
                                                                               .replaceAll("( -)|--", ",")
                                                                               .replaceAll("[, ]a*n+'*d*[: ]", ",")
                                                                               .split(","))  //TODO what am i chatgpt? it's impossible to do this right
                                                                .map(str -> new PlatformContributor(str, new PlatformContact(Map.of())))
                                                                .toArray(PlatformContributor[]::new))
                                    .orElseGet(() -> new PlatformContributor[0]),
                    null,
                    new PlatformContact(new HashMap<>() {{
                        if (mod.getIssueURL() != null)
                            put("issues", mod.getIssueURL().toString());

                        if (meta.getModURL().isPresent())
                            put("download", meta.getModURL().orElseThrow().toString());

                        if (meta.getModProperties().containsKey("displayURL"))
                            put("homepage", (String) meta.getModProperties().get("displayURL"));
                    }}),
                    mod.getLicense() == null ? Set.of() : Set.of(mod.getLicense()),
                    meta.getVersion().getQualifier(),
                    ModEnvironment.ALL,
                    dependencies,
                    PlatformMod.Type.NEOFORGE
            );
        })).collect(Collectors.toList());
    }

    public static Set<Path> getModsPaths() {
        return FMLLoader.getCurrent().getLoadingModList().getModFiles()
                                                         .stream().map(ModFileInfo::getFile)
                                                                  .map(ModFile::getFilePath)
                                                         .collect(Collectors.toSet());
    }

    public static RunningEnv getRunningEnvironment() {
        return FMLEnvironment.getDist().isClient()
                    ? RunningEnv.CLIENT
                    : RunningEnv.SERVER;
    }
}
