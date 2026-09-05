package pl.olafcio.avoid.net.command_client.suggestion;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.command_server.suggestions.Suggestion;
import pl.olafcio.avoid_common.Either;

@ApiStatus.Experimental
public class ClientSuggestion extends Suggestion {
    private Integer background = null,
                    text = null,
                    textActive = null;

    @ApiStatus.Internal
    public ClientSuggestion(int rangeStart, int rangeEnd, String text, Either<BaseComponent<?>, String> tooltip) {
        super(rangeStart, rangeEnd, text, tooltip);
    }

    public Integer backgroundColor() {
        return background;
    }

    public void backgroundColor(int background) {
        this.background = background;
    }

    public void clearBackgroundColor() {
        this.background = null;
    }

    public Integer textColor() {
        return text;
    }

    public void textColor(int text) {
        this.text = text;
    }

    public void clearTextColor() {
        this.text = null;
    }

    public Integer textActiveColor() {
        return textActive;
    }

    public void textActiveColor(int textActive) {
        this.textActive = textActive;
    }

    public void clearTextActiveColor() {
        this.textActive = null;
    }
}
