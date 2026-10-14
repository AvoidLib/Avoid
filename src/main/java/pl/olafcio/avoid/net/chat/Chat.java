package pl.olafcio.avoid.net.chat;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.platform.AvoidWrappedLoader;
import pl.olafcio.avoid.errors.ImproperEnvironment;
import pl.olafcio.avoid.platform.RunningEnv;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid_impl.net.chat.ChatNative;

/**
 * The namespace used to manage the chat on the client.
 * <br/><br/>
 * Send a message with {@link Chat#sendMessage(String) sendMessage([String] msg)}.<br/>
 * Spoof a message with {@link Chat#receiveMessage(BaseComponent) receiveMessage([BaseComponent] msg)}.
 */
@NeverRemoval
public final class Chat {
    @ApiStatus.Internal
    private Chat() {}

    /**
     * Clears the chat.
     * @param clearSentHistory If set to {@code true}, also resets the sent-message history.
     */
    public static void clear(boolean clearSentHistory) {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot Chat#clear from the server!");

        ChatNative.clear(clearSentHistory);
    }

    /**
     * Discards the save of last input in the chat box.
     */
    public static void discardDraft() {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot Chat#discardDraft from the server!");

        ChatNative.discardDraft();
    }

    /**
     * Scrolls a given amount of lines in the chat screen.
     */
    public static void scroll(int amount) {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot Chat#scroll from the server!");

        ChatNative.scroll(amount);
    }

    /**
     * Scrolls to the bottom of the chat screen (most recent).
     */
    public static void resetScroll() {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot Chat#resetScroll from the server!");

        ChatNative.resetScroll();
    }

    /**
     * Sends a message to the server.
     */
    public static void sendMessage(String data) {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot Chat#sendMessage from the server!");

        ChatNative.sendToServer(data);
    }

    /**
     * Adds a message to the chat screen.
     */
    public static void receiveMessage(BaseComponent<?> data) {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot Chat#receiveMessage from the server!");

        ChatNative.sendToClient(data);
    }
}
