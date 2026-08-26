package pl.olafcio.avoid;

import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.api.Sponge;
import pl.olafcio.avoid.mods.loader.AvoidModLoader;

import java.nio.file.Path;
import java.util.Arrays;
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

    /** Returns whether the wrapped loader is Fabric. (This changes during compile time for Avoid builds for other loaders) */
    public static boolean isFabric() { return false; }

    /** Returns whether the wrapped loader is NeoForge. (This changes during compile time for Avoid builds for other loaders) */
    public static boolean isNeoForge() { return false; }

    /** Returns whether the wrapped loader is Sponge. (This changes during compile time for Avoid builds for other loaders) */
    public static boolean isSponge() { return true; }

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
