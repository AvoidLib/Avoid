package pl.olafcio.avoid_impl;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import pl.olafcio.avoid_impl.paper.CommandSystem;
import pl.olafcio.avoid_loader.PreModLoader;

public class PaperBootstrap implements PluginBootstrap {
    static {
        PreModLoader.preload();
    }

    @Override
    public void bootstrap(BootstrapContext context) {
        Avoid.INSTANCE.onEarlyInit();

        context.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            new CommandSystem().finishCommands(commands);
        });
    }
}
