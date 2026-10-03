package pl.olafcio.avoid;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.loader.AvoidModLoader;
import pl.olafcio.avoid.platform.PlatformMod;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * A wrapper over some of the parent running modloader's API.<br/><br/>
 * <b>NOTE:</b> If you want the Avoid addon loader API, take a look at {@link AvoidModLoader}.
 */
@ApiStatus.Experimental
public final class AvoidWrappedLoader {
    @ApiStatus.Internal
    private AvoidWrappedLoader() {}

    /** Returns whether the wrapped loader is Fabric. (This changes during compile time for Avoid builds for other loaders) */
    public static boolean isFabric() { return pl.olafcio.avoid_impl.AvoidWrappedLoader.isFabric(); }

    /** Returns whether the wrapped loader is NeoForge. (This changes during compile time for Avoid builds for other loaders) */
    public static boolean isNeoForge() { return pl.olafcio.avoid_impl.AvoidWrappedLoader.isNeoForge(); }

    /** Returns whether the wrapped loader is Sponge. (This changes during compile time for Avoid builds for other loaders) */
    public static boolean isSponge() { return pl.olafcio.avoid_impl.AvoidWrappedLoader.isSponge(); }

    /** Returns whether the wrapped loader is Paper. (This changes during compile time for Avoid builds for other loaders) */
    public static boolean isPaper() { return pl.olafcio.avoid_impl.AvoidWrappedLoader.isPaper(); }

    /**
     * Returns the game directory.
     */
    public static Path getGameDir() {
        return pl.olafcio.avoid_impl.AvoidWrappedLoader.getGameDir();
    }

    /**
     * Returns whether is a mod using the specified ID present.
     */
    public static boolean isModPresent(String id) {
        return pl.olafcio.avoid_impl.AvoidWrappedLoader.isModPresent(id);
    }

    /**
     * Returns a list of mod metadata records.
     */
    public static List<PlatformMod> getMods() {
        return pl.olafcio.avoid_impl.AvoidWrappedLoader.getMods();
    }

    /**
     * Returns a list containing the JAR path of each loaded mod.
     */
    public static Set<Path> getModsPaths() {
        return pl.olafcio.avoid_impl.AvoidWrappedLoader.getModsPaths();
    }

    /**
     * Returns the type of the running environment.
     */
    public static RunningEnv getRunningEnvironment() {
        return pl.olafcio.avoid_impl.AvoidWrappedLoader.getRunningEnvironment();
    }
}
