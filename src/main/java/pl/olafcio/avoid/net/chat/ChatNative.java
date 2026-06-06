package pl.olafcio.avoid.net.chat;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COToNative;

@Native
@ApiStatus.Internal
public final class ChatNative {
    @ApiStatus.Internal
    private ChatNative() {}

    public static void sendToServer(String data) {
        Avoid.mc.player.connection.sendChat(data);
    }

    public static void sendToClient(BaseComponent<?> data) {
        Avoid.mc.execute(() -> {
            Avoid.mc.gui.getChat().addMessage(COToNative.from(data));
        });
    }
}
