package pl.olafcio.avoid.net.chat.component;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.type.KeymapComponent;
import pl.olafcio.avoid.net.chat.component.type.TextComponent;
import pl.olafcio.avoid.net.chat.component.type.TranslateComponent;
import pl.olafcio.avoid.net.chat.component.type.TranslateFormattedComponent;

@ApiStatus.NonExtendable
public interface Components {
    ;

    static TextComponent literal(String value) {
        return TextComponent.of(value);
    }

    static KeymapComponent keymap(String value) {
        return KeymapComponent.of(value);
    }

    static TranslateComponent translation(String value) {
        return TranslateComponent.of(value);
    }

    static TranslateFormattedComponent translation(String value, Object... format) {
        return TranslateFormattedComponent.of(value, format);
    }

    static TranslateFormattedComponent translationFallback(String value, String fallback) {
        return TranslateFormattedComponent.of(value, fallback);
    }

    static TranslateFormattedComponent translationFallback(String value, String fallback, Object... format) {
        return TranslateFormattedComponent.of(value, fallback, format);
    }
}
