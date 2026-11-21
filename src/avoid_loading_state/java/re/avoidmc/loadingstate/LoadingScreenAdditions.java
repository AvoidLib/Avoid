package re.avoidmc.loadingstate;

import pl.olafcio.avoid.mods.event.EventHandler;
import pl.olafcio.avoid.mods.events_loader.AllModsLoadedEvent;
import pl.olafcio.avoid.mods.events_loader.ModBeforeLoadEvent;
import pl.olafcio.avoid.mods.events_loader.ModEnablingEvent;
import pl.olafcio.avoid.mods.events_loader.ModLoadedEvent;
import pl.olafcio.avoid.mods.events_loader.error.ModErrorEvent;
import pl.olafcio.avoid.mods.events_loader.error.ModFatalEvent;
import pl.olafcio.avoid.net.client.Client;
import pl.olafcio.avoid.net.client_loading.event.ClientLoadingScreenRenderMojangEvent;
import pl.olafcio.avoid.net.util.easing_float.Easing;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public final class LoadingScreenAdditions {
    private static final ArrayList<String> state
                   = new ArrayList<>();

    private static final HashMap<String, Message> history
                   = new HashMap<>();

    private static final class Message {
        public final long addTime;
        public       int offset;

        private Message(long addTime, int offset) {
            this.addTime = addTime;
            this.offset = offset;
        }
    }

    @EventHandler
    public static void onModLoading(ModBeforeLoadEvent event) {
        state.add("Loading " + event.id());
    }

    @EventHandler
    public static void onModLoaded(ModLoadedEvent event) {
        history.put("Loaded " + event.meta().id(), new Message(System.currentTimeMillis(), 0));
    }

    @EventHandler
    public static void onAllModsLoaded(AllModsLoadedEvent event) {
        state.add("All addons have been loaded");
    }

    @EventHandler
    public static void onModEnabling(ModEnablingEvent event) {
        state.add("Enabling " + event.meta().id());
    }

    @EventHandler
    public static void onModError(ModErrorEvent event) {
        state.add("Error occurred while loading mod '%s'".formatted(event.modInstance().metadata().id()));
    }

    @EventHandler
    public static void onModFatal(ModFatalEvent event) {
        state.add("Fatal error occurred while loading mod '%s'".formatted(event.modInstance().metadata().id()));
    }

    @EventHandler
    public static void onLoading(ClientLoadingScreenRenderMojangEvent event) {
        if (!state.isEmpty()) {
            event.ctx().fill(5, 5, 6, 5 + 3*2 + Client.getFont().height(), new Color(0xF85151).getRGB());
            event.ctx().drawString(Client.getFont(), state.getLast(), 7, 8, new Color(0xFFFFFFFF).getRGB());
        }

        int i = 1;

        for (var element : history.entrySet()) {
            event.ctx().matrixStack().pushMatrix();
            event.ctx().matrixStack().translate(0, element.getValue().offset++);

            event.ctx().drawString(
                    Client.getFont(),
                    element.getKey(),
                    6, event.ctx().getHeight() - (6 - Client.getFont().height()) * i++,
                    new Color(239 / 255F, 239 / 255F, 239 / 255F, Easing.QUINT_IN.apply(Math.clamp((System.currentTimeMillis() - (element.getValue().addTime + 2000)) / 1500F, 0, 1))).getRGB()
            );

            event.ctx().matrixStack().popMatrix();
        }
    }
}
