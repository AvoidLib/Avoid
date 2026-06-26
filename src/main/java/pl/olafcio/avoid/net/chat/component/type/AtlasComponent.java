package pl.olafcio.avoid.net.chat.component.type;

import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.id.Identification;

import java.util.UUID;

@WillRefactor(aspect = "name, fields, methods, superclass")
public class AtlasComponent extends BaseComponent<AtlasComponent> {
    private final Identification atlas;
    private final Identification sprite;

    protected AtlasComponent(Identification atlas, Identification sprite) {
        this.atlas = atlas;
        this.sprite = sprite;
    }

    public Identification atlas() {
        return atlas;
    }
    public Identification sprite() {
        return sprite;
    }

    public static AtlasComponent of(Identification atlas, Identification sprite) {
        return new AtlasComponent(atlas, sprite);
    }
}
