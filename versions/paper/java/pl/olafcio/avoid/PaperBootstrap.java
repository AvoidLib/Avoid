package pl.olafcio.avoid;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import pl.olafcio.avoid.paper.CommandSystem;

public class PaperBootstrap implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {
        Avoid.INSTANCE.onEarlyInit();

        context.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            new CommandSystem().finishCommands(commands);
        });
    }
}
