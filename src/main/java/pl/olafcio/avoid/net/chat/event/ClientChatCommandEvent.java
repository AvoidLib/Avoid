package pl.olafcio.avoid.net.chat.event;

import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.mods.event.Cancellable;

import java.util.Objects;

public final class ClientChatCommandEvent extends Cancellable {
    private final @NotNull String message;

    public ClientChatCommandEvent(@NotNull String message) {
        this.message = message;
    }

    public @NotNull String message() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ClientChatCommandEvent) obj;
        return Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "ClientChatCommandEvent[" +
                "message=" + message + ']';
    }
}
