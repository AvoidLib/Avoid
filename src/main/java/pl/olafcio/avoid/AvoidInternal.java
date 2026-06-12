package pl.olafcio.avoid;

import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class AvoidInternal {
    @ApiStatus.Internal
    private AvoidInternal() {}

    @ApiStatus.Internal
    public static MinecraftServer server;

    @ApiStatus.Internal
    public static MinecraftServer getServer() {
        return server;
    }
}
