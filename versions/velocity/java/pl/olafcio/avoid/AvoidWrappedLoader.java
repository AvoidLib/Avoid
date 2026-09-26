package pl.olafcio.avoid;

import pl.olafcio.avoid.annotations.dist.Dist;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.loader.AvoidModLoader;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.olafcio.avoid_impl.AvoidInternal.server;

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
    public static boolean isSponge() { return false; }

    /** Returns whether the wrapped loader is Paper. (This changes during compile time for Avoid builds for other loaders) */
    public static boolean isPaper() { return false; }

    /**
     * Returns the game directory.
     */
    public static Path getGameDir() {
        return Path.of(".");
    }

    /**
     * Returns whether is a mod using the specified ID present.
     */
    public static boolean isModPresent(String id) {
        return server.getPluginManager().isLoaded(id);
    }

    /**
     * Returns a list containing the JAR path of each loaded mod.
     */
    public static Set<Path> getModsPaths() {
        return server.getPluginManager().getPlugins().stream()
                                                     .map(plug -> plug.getDescription().getSource().orElseThrow())
                                                     .collect(Collectors.toSet());
    }

    /**
     * Returns the type of the running environment.
     */
    public static RunningEnv getRunningEnvironment() {
        return RunningEnv.SERVER;
    }
}
