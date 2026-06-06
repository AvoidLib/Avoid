package pl.olafcio.avoid.net.chat.component.type;

import pl.olafcio.avoid.net.chat.component.BaseComponent;

public class TextComponent extends BaseComponent<TextComponent> {
    private final String text;

    protected TextComponent(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }

    public static TextComponent of(String text) {
        return new TextComponent(text);
    }
}
