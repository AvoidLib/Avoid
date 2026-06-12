package pl.olafcio.avoid.net.chat_server.event;

import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.player.Player;

import java.util.Objects;

/**
 * Fired when the server sends a system message to a player.<br/>
 * This event won't be fired for player-sent messages if a mod like No Chat Reports isn't installed.
 */
public final class ServerChatSendEvent {
    private final @NotNull Player player;
    private final @NotNull BaseComponent<?> message;
    private boolean cancelled;

    public ServerChatSendEvent(@NotNull Player player, @NotNull BaseComponent<?> message) {
        this.player = player;
        this.message = message;
        this.cancelled = false;
    }

    public @NotNull Player player() {
        return player;
    }

    public @NotNull BaseComponent<?> message() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ServerChatSendEvent) obj;
        return Objects.equals(this.player, that.player) &&
                Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, message);
    }

    @Override
    public String toString() {
        return "ServerChatSendEvent[" +
                "player=" + player + ", " +
                "message=" + message + ']';
    }

    public void cancel() {
        this.cancelled = true;
    }

    public void setCancelled(boolean value) {
        this.cancelled = value;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }
}
