package pl.olafcio.avoid.net.client_loading.event;

import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class ClientLoadingScreenRenderMojangEventNative {
    @ApiStatus.Internal
    private ClientLoadingScreenRenderMojangEventNative() {}

    public static ClientLoadingScreenRenderMojangEvent getEvent(GuiGraphics guiGraphics) {
        var event = ClientLoadingScreenRenderMojangEvent.INSTANCE;

        event.guiGraphics = guiGraphics;

        return event;
    }
}
