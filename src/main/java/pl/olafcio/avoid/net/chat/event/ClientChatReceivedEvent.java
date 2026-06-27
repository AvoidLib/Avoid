package pl.olafcio.avoid.net.chat.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.tag.ChatTag;

import java.util.Objects;

public final class ClientChatReceivedEvent extends Cancellable {
    private final @NotNull BaseComponent<?> message;
    private final @Nullable ChatTag tag;

    public ClientChatReceivedEvent(@NotNull BaseComponent<?> message, @Nullable ChatTag tag) {
        this.message = message;
        this.tag = tag;
    }

    public @NotNull BaseComponent<?> message() {
        return message;
    }

    public @Nullable ChatTag tag() {
        return tag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ClientChatReceivedEvent) obj;
        return Objects.equals(this.message, that.message) &&
                Objects.equals(this.tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, tag);
    }

    @Override
    public String toString() {
        return "ClientChatReceivedEvent[" +
                "message=" + message + ", " +
                "tag=" + tag + ']';
    }
}
