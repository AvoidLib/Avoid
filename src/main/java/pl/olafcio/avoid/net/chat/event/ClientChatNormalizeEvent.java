package pl.olafcio.avoid.net.chat.event;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.mods.event.Cancellable;

import java.util.Objects;

@ApiStatus.Experimental
public final class ClientChatNormalizeEvent {
    private final @NotNull String input;
    private       @NotNull String output;

    public ClientChatNormalizeEvent(@NotNull String input, @NotNull String output) {
        this.input = input;
        this.output = output;
    }

    public @NotNull String input() {
        return input;
    }

    public @NotNull String output() {
        return output;
    }

    public void setOutput(@NotNull String value) {
        output = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        var that = (ClientChatNormalizeEvent) obj;

        return Objects.equals(this.input, that.input) &&
               Objects.equals(this.output, that.output);
    }

    @Override
    public int hashCode() {
        return Objects.hash(input, output);
    }

    @Override
    public String toString() {
        return "ClientChatNormalizeEvent[" +
                "input=" + input + ", " +
                "output=" + output + ']';
    }
}
