package pl.olafcio.avoid_impl;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;
import pl.olafcio.avoid.Avoid;

@Plugin(id = "avoidlib", name = "AvoidLib", version = "1.27",
        url = "https://modrinth.com/mod/avoid", description = "Yet another library for everything Minecraft. Kinda like Bukkit.", authors = {"Olafcio"})
public final class A4Velocity {
    @Inject
    public A4Velocity(ProxyServer server, Logger logger) {
        AvoidInternal.server = server;
        AvoidInternal.plugin = this;

        Avoid.LOGGER = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Avoid.INSTANCE.onEarlyInit();
        Avoid.INSTANCE.onInitialize();
    }
}
