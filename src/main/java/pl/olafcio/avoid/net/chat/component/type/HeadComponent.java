package pl.olafcio.avoid.net.chat.component.type;

import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

import java.util.UUID;

@WillRefactor(aspect = "name, fields, methods, superclass")
public class HeadComponent extends BaseComponent<HeadComponent> {
    private final UUID uuid;
    private final boolean withHat;

    protected HeadComponent(UUID uuid, boolean withHat) {
        this.uuid = uuid;
        this.withHat = withHat;
    }

    public UUID uuid() {
        return uuid;
    }
    public boolean withHat() {
        return withHat;
    }

    public static HeadComponent of(UUID uuid, boolean withHat) {
        return new HeadComponent(uuid, withHat);
    }
}
