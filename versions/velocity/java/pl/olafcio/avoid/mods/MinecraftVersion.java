package pl.olafcio.avoid.mods;

import com.velocitypowered.api.network.ProtocolVersion;
import org.jetbrains.annotations.ApiStatus;

import java.util.Date;

@ApiStatus.Experimental
public final class MinecraftVersion {
    @ApiStatus.Internal
    private MinecraftVersion() {}

    public static String get() {
        return ProtocolVersion.MAXIMUM_VERSION.getMostRecentSupportedVersion();
    }

    public static boolean isRelease() {
        return ProtocolVersion.MAXIMUM_VERSION.isSupported();
    }

    public static int getProtocolVersion() {
        return ProtocolVersion.MAXIMUM_VERSION.getProtocol();
    }

    public static Date getBuildTime() {
        return null;
    }
}
