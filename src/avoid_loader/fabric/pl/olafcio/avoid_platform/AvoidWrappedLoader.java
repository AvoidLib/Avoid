package pl.olafcio.avoid_platform;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModDependency;
import net.fabricmc.loader.api.metadata.ModEnvironment;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.platform.RunningEnv;
import pl.olafcio.avoid.platform.mod.PlatformContributor;
import pl.olafcio.avoid.platform.mod.PlatformContact;
import pl.olafcio.avoid.platform.mod.PlatformDependency;
import pl.olafcio.avoid.platform.mod.PlatformMod;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApiStatus.Internal
public final class AvoidWrappedLoader {
    @ApiStatus.Internal
    private AvoidWrappedLoader() {}

    /** Returns whether the wrapped loader is Fabric. (This changes during compile time for Avoid builds for other loaders) */
    public static boolean isFabric() { return true; }

    /** Returns whether the wrapped loader is NeoForge. (This changes during compile time for Avoid builds for other loaders) */
    public static boolean isNeoForge() { return false; }

    /** Returns whether the wrapped loader is Sponge. (This changes during compile time for Avoid builds for other loaders) */
    public static boolean isSponge() { return false; }

    /** Returns whether the wrapped loader is Paper. (This changes during compile time for Avoid builds for other loaders) */
    public static boolean isPaper() { return false; }

    /**
     * Returns the game directory.
     */
    public static Path getGameDir() {
        return FabricLoader.getInstance().getGameDir();
    }

    /**
     * Returns whether is a mod using the specified ID present.
     */
    public static boolean isModPresent(String id) {
        return FabricLoader.getInstance().getModContainer(id).isPresent();
    }

    /**
     * Returns a list of mod metadata records.
     */
    public static List<PlatformMod> getMods() {
        return FabricLoader.getInstance().getAllMods().stream().map(mod -> {
            var meta = mod.getMetadata();
            var dependencies = new ArrayList<PlatformDependency>();

            for (var dependency : meta.getDependencies())
                dependencies.add(new PlatformDependency(
                        dependency.getModId(),
                        dependency.getKind() == ModDependency.Kind.BREAKS     ? PlatformDependency.Hard.INCOMPATIBLE :
                        dependency.getKind() == ModDependency.Kind.CONFLICTS  ? PlatformDependency.Soft.INCOMPATIBLE :
                        dependency.getKind() == ModDependency.Kind.DEPENDS    ? PlatformDependency.Hard.REQUIRES     :
                        dependency.getKind() == ModDependency.Kind.RECOMMENDS ? PlatformDependency.Hard.RECOMMENDS   :
                        dependency.getKind() == ModDependency.Kind.SUGGESTS   ? PlatformDependency.Soft.RECOMMENDS   :
                                                                                null,
                        PlatformDependency.LoadOrder.BEFORE_MOD
                ));

            for (var id : meta.getProvides())
                dependencies.add(new PlatformDependency(
                        id,
                        PlatformDependency.Soft.PROVIDES,
                        PlatformDependency.LoadOrder.BEFORE_MOD
                ));

            return new PlatformMod(
                    meta.getId(),
                    meta.getName(),
                    meta.getDescription(),
                    meta.getAuthors().stream()
                                     .map(person -> new PlatformContributor(
                                                                    person.getName(),
                                                                    new PlatformContact(person.getContact().asMap())
                                                           )
                                     )
                                     .toArray(PlatformContributor[]::new),
                    meta.getContributors().stream()
                                          .map(person -> new PlatformContributor(
                                                                    person.getName(),
                                                                    new PlatformContact(person.getContact().asMap())
                                                                )
                                          )
                                          .toArray(PlatformContributor[]::new), new PlatformContact(meta.getContact().asMap()),
                    meta.getLicense().stream().collect(Collectors.toUnmodifiableSet()),
                    meta.getVersion().getFriendlyString(),
                    meta.getEnvironment() == ModEnvironment.CLIENT    ? pl.olafcio.avoid.util.ModEnvironment.CLIENT :
                    meta.getEnvironment() == ModEnvironment.UNIVERSAL ? pl.olafcio.avoid.util.ModEnvironment.ALL    :
                                                                        pl.olafcio.avoid.util.ModEnvironment.SERVER,
                    dependencies,
                    meta.getType().equals("builtin") ? PlatformMod.Type.BUILTIN :
                    meta.getType().equals("fabric")  ? PlatformMod.Type.FABRIC  :
                                                       PlatformMod.Type.CUSTOM
            );
        }).collect(Collectors.toList());
    }

    /**
     * Returns a list containing the JAR path of each loaded mod.
     */
    public static Set<Path> getModsPaths() {
        return FabricLoader.getInstance().getAllMods().stream().map(ModContainer::getRootPaths)
                                                               .flatMap(Collection::stream)
                                                      .collect(Collectors.toSet());
    }

    /**
     * Returns the type of the running environment.
     */
    public static RunningEnv getRunningEnvironment() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT
                    ? RunningEnv.CLIENT
                    : RunningEnv.SERVER;
    }
}
