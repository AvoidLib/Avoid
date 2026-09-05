package pl.olafcio.avoid.mixinclass;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mixininterface.IClientSuggestion;

@ApiStatus.Internal
public class AvoidClientSuggestion extends Suggestion implements IClientSuggestion {
    private final Integer textColor,
                          textActiveColor,
                          backgroundColor;

    public AvoidClientSuggestion(StringRange range, String text, Message tooltip, Integer textColor, Integer textActiveColor, Integer backgroundColor) {
        super(range, text, tooltip);

        this.textColor = textColor;
        this.textActiveColor = textActiveColor;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public Integer avoid$backgroundColor() {
        return backgroundColor;
    }

    @Override
    public Integer avoid$textColor() {
        return textColor;
    }

    @Override
    public Integer avoid$textActiveColor() {
        return textActiveColor;
    }
}
