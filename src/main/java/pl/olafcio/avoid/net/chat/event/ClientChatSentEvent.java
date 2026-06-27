package pl.olafcio.avoid.net.chat.event;

import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.mods.event.Cancellable;

import java.util.Objects;

@NeverRemoval
public final class ClientChatSentEvent extends Cancellable {
    private final @NotNull String message;

    public ClientChatSentEvent(@NotNull String message) {
        this.message = message;
    }

    public @NotNull String message() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ClientChatSentEvent) obj;
        return Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "ClientChatSentEvent[" +
                "message=" + message + ']';
    }
}
