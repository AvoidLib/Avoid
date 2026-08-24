package pl.olafcio.avoid;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.LoadedGameEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin("avoidlib")
public class A4Sponge {
    @Listener
    public void onLoadedGame(LoadedGameEvent e) {
        Avoid.INSTANCE.onInitialize();
    }
}
