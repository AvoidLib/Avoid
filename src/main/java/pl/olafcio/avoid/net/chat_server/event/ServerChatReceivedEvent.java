package pl.olafcio.avoid.net.chat_server.event;

import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.player.Player;

import java.util.Objects;

/**
 * Fired when a player sends a message.
 */
@NeverRemoval
public final class ServerChatReceivedEvent {
    private final @NotNull String message;
    private final @NotNull Player player;
    private boolean cancelled;

    public ServerChatReceivedEvent(@NotNull String message, @NotNull Player player) {
        this.message = message;
        this.player = player;
        this.cancelled = false;
    }

    public @NotNull String message() {
        return message;
    }

    public @NotNull Player player() {
        return player;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ServerChatReceivedEvent) obj;
        return Objects.equals(this.message, that.message) &&
                Objects.equals(this.player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, player);
    }

    @Override
    public String toString() {
        return "ServerChatReceivedEvent[" +
                "message=" + message + ", " +
                "player=" + player + ']';
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
