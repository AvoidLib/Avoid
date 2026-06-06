package pl.olafcio.avoid.net.chat.component.type;

import pl.olafcio.avoid.net.chat.component.BaseComponent;

public class TranslateComponent extends BaseComponent<TranslateComponent> {
    private final String translate;
    private final String fallback;

    protected TranslateComponent(String translate) {
        this.translate = translate;
        this.fallback = null;
    }

    protected TranslateComponent(String translate, String fallback) {
        this.translate = translate;
        this.fallback = fallback;
    }

    public String translate() {
        return translate;
    }
    public String fallback() {
        return fallback;
    }

    public static TranslateComponent of(String text) {
        return new TranslateComponent(text);
    }
    public static TranslateComponent of(String text, String fallback) {
        return new TranslateComponent(text, fallback);
    }
}
