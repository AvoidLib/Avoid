package pl.olafcio.avoid.mods;

import net.minecraft.SharedConstants;
import org.jetbrains.annotations.ApiStatus;

import java.util.Date;

@ApiStatus.Experimental
public final class MinecraftVersion {
    @ApiStatus.Internal
    private MinecraftVersion() {}

    public static String get() {
        return SharedConstants.getCurrentVersion().name();
    }

    public static boolean isRelease() {
        return SharedConstants.getCurrentVersion().stable();
    }

    public static int getProtocolVersion() {
        return SharedConstants.getCurrentVersion().protocolVersion();
    }

    public static Date getBuildTime() {
        return SharedConstants.getCurrentVersion().buildTime();
    }
}
