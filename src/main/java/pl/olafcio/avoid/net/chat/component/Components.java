package pl.olafcio.avoid.net.chat.component;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.chat.component.type.KeymapComponent;
import pl.olafcio.avoid.net.chat.component.type.TextComponent;
import pl.olafcio.avoid.net.chat.component.type.TranslateComponent;
import pl.olafcio.avoid.net.chat.component.type.TranslateFormattedComponent;

@NeverRemoval
@ApiStatus.NonExtendable
public interface Components {
    ;

    @NeverRemoval
    static TextComponent literal(String value) {
        return TextComponent.of(value);
    }

    @ApiStatus.Experimental
    static KeymapComponent keymap(String value) {
        return KeymapComponent.of(value);
    }

    @NeverRemoval
    static TranslateComponent translation(String value) {
        return TranslateComponent.of(value);
    }

    @NeverRemoval
    static TranslateFormattedComponent translation(String value, Object... format) {
        return TranslateFormattedComponent.of(value, format);
    }

    @NeverRemoval
    static TranslateFormattedComponent translationFallback(String value, String fallback) {
        return TranslateFormattedComponent.of(value, fallback);
    }

    @NeverRemoval
    static TranslateFormattedComponent translationFallback(String value, String fallback, Object... format) {
        return TranslateFormattedComponent.of(value, fallback, format);
    }
}
