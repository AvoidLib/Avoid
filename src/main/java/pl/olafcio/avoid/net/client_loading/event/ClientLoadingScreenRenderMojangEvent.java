package pl.olafcio.avoid.net.client_loading.event;

import net.minecraft.client.gui.GuiGraphics;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.screen.Drawer;
import pl.olafcio.avoid.net.screen.DrawerNative;

public final class ClientLoadingScreenRenderMojangEvent extends Cancellable {
    static final ClientLoadingScreenRenderMojangEvent INSTANCE
           = new ClientLoadingScreenRenderMojangEvent();

    Object guiGraphics;

    private ClientLoadingScreenRenderMojangEvent() {}

    public Drawer ctx() {
        return DrawerNative.create((GuiGraphics) guiGraphics);
    }
}
