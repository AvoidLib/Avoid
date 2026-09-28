package pl.olafcio.avoid_impl;

import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.api.Sponge;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.metadata.model.PluginDependency;
import pl.olafcio.avoid.mods.ModEnvironment;
import pl.olafcio.avoid.mods.loader.AvoidModLoader;
import pl.olafcio.avoid.platform.PlatformContributor;
import pl.olafcio.avoid.platform.PlatformDependency;
import pl.olafcio.avoid.platform.PlatformMod;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A wrapper over some of the parent running modloader's API.<br/><br/>
 * <b>NOTE:</b> If you want the Avoid addon loader API, take a look at {@link AvoidModLoader}.
 */
@ApiStatus.Experimental
public final class AvoidWrappedLoader {
    @ApiStatus.Internal
    private AvoidWrappedLoader() {}

    public static boolean isFabric() { return false; }
    public static boolean isNeoForge() { return false; }
    public static boolean isSponge() { return true; }
    public static boolean isPaper() { return false; }

    /**
     * Returns the game directory.
     */
    public static Path getGameDir() {
        return Sponge.game().gameDirectory();
    }

    /**
     * Returns whether is a mod using the specified ID present.
     */
    public static boolean isModPresent(String id) {
        return Sponge.game().pluginManager().plugin(id).isPresent();
    }

    /**
     * Returns a list of mod metadata records.
     */
    public static List<PlatformMod> getMods() {
        return Sponge.game().pluginManager().plugins().stream().map(mod -> {
            var meta = mod.metadata();
            var dependencies = new ArrayList<PlatformDependency>();

            for (var dependency : meta.dependencies())
                dependencies.add(new PlatformDependency(
                        dependency.id(),
                        dependency.optional()
                                ? PlatformDependency.Hard.RECOMMENDS
                                : PlatformDependency.Hard.REQUIRES,
                        dependency.loadOrder() == PluginDependency.LoadOrder.BEFORE ? PlatformDependency.LoadOrder.BEFORE_MOD :
                        dependency.loadOrder() == PluginDependency.LoadOrder.AFTER  ? PlatformDependency.LoadOrder.AFTER_MOD  :
                                                                                      PlatformDependency.LoadOrder.UNDEFINED
                ));

            for (var dependency : meta.conflicts())
                dependencies.add(new PlatformDependency(
                        dependency.id(),
                        dependency.fatal()
                                ? PlatformDependency.Hard.INCOMPATIBLE
                                : PlatformDependency.Soft.INCOMPATIBLE,
                        PluginDependency.LoadOrder.BEFORE_MOD
                ));

            return new PlatformMod(
                    meta.id(),
                    meta.name().orElseThrow(),
                    meta.description().orElseThrow(),
                    new PlatformContributor[0],
                    meta.contributors().stream()
                            .map(person -> new PlatformContributor(
                                                                person.name(),
                                                                new PlatformContact(Map.of())
                                                           )
                            )
                            .toArray(PlatformContributor[]::new),
                    new PlatformContact(Map.of()),
                    meta.license().isPresent() ? Set.of(meta.license().orElseThrow()) : Set.of(),
                    meta.version().getQualifier(),
                    ModEnvironment.ALL,
                    dependencies,
                    PlatformMod.Type.SPONGE
            );
        }).collect(Collectors.toList());
    }

    /**
     * Returns a list containing the JAR path of each loaded mod.
     */
    public static Set<Path> getModsPaths() {
        return Sponge.game().pluginManager().plugins().stream()
                                                      .map(plug -> plug.locateResource("/").orElseThrow())
                                                      .map(res -> Arrays.stream(res.toString().split(":")).toList())
                                                      .map(list -> {
                                                          return list.get(list.size() - 2) + list.getLast().split("!/")[0];
                                                      })
                                                      .map(path -> {
                                                          while (path.startsWith("/"))
                                                              path = path.substring(1);

                                                          return path;
                                                      })
                                                      .map(Path::of)
                                                      .collect(Collectors.toSet());
    }

    /**
     * Returns the type of the running environment.
     */
    public static RunningEnv getRunningEnvironment() {
        return Sponge.game().platform().type().isClient()
                ? RunningEnv.CLIENT
                : RunningEnv.SERVER;
    }
}
