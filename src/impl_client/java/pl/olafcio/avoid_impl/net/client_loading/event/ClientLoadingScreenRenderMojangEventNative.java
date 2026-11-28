package pl.olafcio.avoid_impl.net.client_loading.event;

import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.client_loading.event.ClientLoadingScreenRenderMojangEvent;
import pl.olafcio.avoid_impl.net.screen.DrawerNative;

@Native
@ApiStatus.Internal
public final class ClientLoadingScreenRenderMojangEventNative {
    @ApiStatus.Internal
    private ClientLoadingScreenRenderMojangEventNative() {}

    private static GuiGraphics guiGraphics;

    public static final ClientLoadingScreenRenderMojangEvent INSTANCE
                  = new ClientLoadingScreenRenderMojangEvent(() -> DrawerNative.create(guiGraphics));

    public static ClientLoadingScreenRenderMojangEvent getEvent(GuiGraphics guiGraphics) {
        ClientLoadingScreenRenderMojangEventNative.guiGraphics = guiGraphics;

        return INSTANCE;
    }
}
