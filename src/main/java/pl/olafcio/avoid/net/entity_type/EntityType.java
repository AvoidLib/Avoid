package pl.olafcio.avoid.net.entity_type;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.id.Identification;

@ApiStatus.NonExtendable
@NeverRemoval
public abstract class EntityType {
    @WillRefactor(aspect = "name")
    public abstract BaseComponent<?> getDescription();

    @NeverRemoval
    public abstract Identification getID();

    public static EntityType of(Identification id) {
        return pl.olafcio.avoid_impl.net.entity_type.EntityType.of(id);
    }

    public static EntityType of(String id) {
        return pl.olafcio.avoid_impl.net.entity_type.EntityType.of(id);
    }
}
