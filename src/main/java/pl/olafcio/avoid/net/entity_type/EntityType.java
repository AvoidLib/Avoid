package pl.olafcio.avoid.net.entity_type;

import net.minecraft.core.registries.BuiltInRegistries;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;

@NeverRemoval
@SuppressWarnings("ClassCanBeRecord")
public final class EntityType {
    final net.minecraft.world.entity.EntityType<?> entityType;

    EntityType(net.minecraft.world.entity.EntityType<?> entityType) {
        this.entityType = entityType;
    }

    @WillRefactor(aspect = "name")
    public BaseComponent<?> getDescription() {
        return COFromNative.from(entityType.getDescription());
    }

    public static EntityType of(Identification id) {
        var mcItem = BuiltInRegistries.ENTITY_TYPE.getValue(IdentificationNative.convert(id));
        return new EntityType(mcItem);
    }

    public static EntityType of(String id) {
        var mcItem = BuiltInRegistries.ENTITY_TYPE.getValue(IdentificationNative.convert(id));
        return new EntityType(mcItem);
    }
}
