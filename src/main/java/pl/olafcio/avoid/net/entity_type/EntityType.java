package pl.olafcio.avoid.net.entity_type;

import net.minecraft.core.registries.BuiltInRegistries;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;

@SuppressWarnings("ClassCanBeRecord")
public final class EntityType {
    final net.minecraft.world.entity.EntityType<?> entityType;

    EntityType(net.minecraft.world.entity.EntityType<?> entityType) {
        this.entityType = entityType;
    }

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
