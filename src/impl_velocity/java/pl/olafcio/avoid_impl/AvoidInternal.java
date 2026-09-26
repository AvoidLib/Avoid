package pl.olafcio.avoid_impl;

import com.velocitypowered.api.proxy.ProxyServer;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class AvoidInternal {
    private AvoidInternal() {}

    public static ProxyServer server;

    public static ProxyServer getServer() {
        return server;
    }
}
