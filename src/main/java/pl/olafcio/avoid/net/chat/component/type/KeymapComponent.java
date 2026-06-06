package pl.olafcio.avoid.net.chat.component.type;

import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

@WillRefactor(aspect = "name, fields, methods, superclass")
public class KeymapComponent extends BaseComponent<KeymapComponent> {
    private final String keybind;

    protected KeymapComponent(String keybind) {
        this.keybind = keybind;
    }

    public String keybind() {
        return keybind;
    }

    public static KeymapComponent of(String text) {
        return new KeymapComponent(text);
    }
}
