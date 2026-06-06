package pl.olafcio.avoid.net.chat.component.type;

import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

import java.util.Arrays;

@WillRefactor(aspect = "name, fields, methods, superclass")
public class TranslateFormattedComponent extends BaseComponent<TranslateFormattedComponent> {
    private final String translate;
    private final String fallback;
    private final BaseComponent<?>[] format;

    protected TranslateFormattedComponent(String translate, Object... formatted) {
        this.translate = translate;
        this.fallback = null;
        this.format = Arrays.stream(formatted)
                            .map(o -> o instanceof BaseComponent<?> c
                                                ? c
                                                : TextComponent.of(String.valueOf(o)))
                            .toArray(BaseComponent<?>[]::new);
    }

    protected TranslateFormattedComponent(String translate, String fallback, Object... formatted) {
        this.translate = translate;
        this.fallback = fallback;
        this.format = Arrays.stream(formatted)
                            .map(o -> o instanceof BaseComponent<?> c
                                    ? c
                                    : TextComponent.of(String.valueOf(o)))
                            .toArray(BaseComponent<?>[]::new);
    }

    public String translate() {
        return translate;
    }
    public String fallback() {
        return fallback;
    }
    public BaseComponent<?>[] format() {
        return format;
    }

    public static TranslateFormattedComponent of(String translate, Object... formatted) {
        return new TranslateFormattedComponent(translate, formatted);
    }

    public static TranslateFormattedComponent of(String translate, String fallback, Object... formatted) {
        return new TranslateFormattedComponent(translate, fallback, formatted);
    }
}
