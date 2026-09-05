package pl.olafcio.avoid.net.command_server.suggestions;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid_common.Either;

import java.util.Objects;

@ApiStatus.Experimental
public class Suggestion {
    private final int rangeStart;
    private final int rangeEnd;
    private final String text;
    private final Either<BaseComponent<?>, String> tooltip;

    @ApiStatus.Internal
    public Suggestion(
            int rangeStart, int rangeEnd,
            String text,
            Either<BaseComponent<?>, String> tooltip
    ) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.text = text;
        this.tooltip = tooltip;
    }

    public int rangeStart() {
        return rangeStart;
    }

    public int rangeEnd() {
        return rangeEnd;
    }

    public String text() {
        return text;
    }

    public Either<BaseComponent<?>, String> tooltip() {
        return tooltip;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Suggestion) obj;
        return this.rangeStart == that.rangeStart &&
                this.rangeEnd == that.rangeEnd &&
                Objects.equals(this.text, that.text) &&
                Objects.equals(this.tooltip, that.tooltip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rangeStart, rangeEnd, text, tooltip);
    }

    @Override
    public String toString() {
        return "Suggestion[" +
                "rangeStart=" + rangeStart + ", " +
                "rangeEnd=" + rangeEnd + ", " +
                "text=" + text + ", " +
                "tooltip=" + tooltip + ']';
    }
}
