package pl.olafcio.avoid_impl.net.entity_type;

import net.minecraft.core.registries.BuiltInRegistries;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.entity_type.UnknownEntityTypeError;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid_impl.net.chat.converter.COFromNative;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;

public final class EntityType extends pl.olafcio.avoid.net.entity_type.EntityType {
    final net.minecraft.world.entity.EntityType<?> entityType;

    EntityType(net.minecraft.world.entity.EntityType<?> entityType) {
        this.entityType = entityType;
    }

    @WillRefactor(aspect = "name")
    @Override
    public BaseComponent<?> getDescription() {
        return COFromNative.from(entityType.getDescription());
    }

    @NeverRemoval
    @Override
    public Identification getID() {
        return IdentificationNative.convertFrom(BuiltInRegistries.ENTITY_TYPE.getKey(entityType));
    }

    public static EntityType of(Identification id) {
        var mcEntity = BuiltInRegistries.ENTITY_TYPE.getValue(IdentificationNative.convert(id));
        var avoidEntity = new EntityType(mcEntity);

        if (!avoidEntity.getID().equals(id))
            throw new UnknownEntityTypeError("Entity type '%s' not found; did you forget to register it, or is the ID wrong?".formatted(
                    id.toString()
            ));

        return avoidEntity;
    }

    public static EntityType of(String id) {
        var avoidID = IdentificationNative.convert(id);
        var mcEntity = BuiltInRegistries.ENTITY_TYPE.getValue(avoidID);

        if (!BuiltInRegistries.ENTITY_TYPE.getKey(mcEntity).equals(avoidID))
            throw new UnknownEntityTypeError("Entity type '%s' not found; did you forget to register it, or is the ID wrong?".formatted(
                    avoidID.toString()
            ));

        return new EntityType(mcEntity);
    }
}
