package pl.olafcio.avoid_impl.net.chat;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid_impl.client.AvoidLibClient;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid_impl.net.chat.converter.COToNative;

@Native
@ApiStatus.Internal
public final class ChatNative {
    @ApiStatus.Internal
    private ChatNative() {}

    public static void clear(boolean clearSentHistory) {
        AvoidLibClient.mc.gui.getChat().clearMessages(clearSentHistory);
    }

    public static void discardDraft() {
        AvoidLibClient.mc.gui.getChat().discardDraft();
    }

    public static void scroll(int amount) {
        AvoidLibClient.mc.gui.getChat().scrollChat(amount);
    }

    public static void resetScroll() {
        AvoidLibClient.mc.gui.getChat().resetChatScroll();
    }

    public static void sendToServer(String data) {
        AvoidLibClient.mc.player.connection.sendChat(data);
    }

    public static void sendToClient(BaseComponent<?> data) {
        AvoidLibClient.mc.execute(() -> {
            AvoidLibClient.mc.gui.getChat().addMessage(COToNative.from(data));
        });
    }
}
