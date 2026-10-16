package pl.olafcio.avoid.mods;

import org.jetbrains.annotations.ApiStatus;

import java.util.Date;

/**
 * A namespace for getting information about the running Minecraft version.
 */
@ApiStatus.Experimental
public final class MinecraftVersion {
    @ApiStatus.Internal
    private MinecraftVersion() {}

    /**
     * Returns the name of the running Minecraft version.
     */
    public static String get() {
        return pl.olafcio.avoid_impl.mods.MinecraftVersion.get();
    }

    /**
     * Returns whether the running Minecraft version is a release version (not a snapshot).
     */
    public static boolean isRelease() {
        return pl.olafcio.avoid_impl.mods.MinecraftVersion.isRelease();
    }

    /**
     * Returns the protocol version of the running Minecraft version.
     */
    public static int getProtocolVersion() {
        return pl.olafcio.avoid_impl.mods.MinecraftVersion.getProtocolVersion();
    }

    /**
     * Returns the date when the running Minecraft version was built.
     */
    public static Date getBuildTime() {
        return pl.olafcio.avoid_impl.mods.MinecraftVersion.getBuildTime();
    }
}
