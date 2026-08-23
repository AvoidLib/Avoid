package pl.olafcio.avoid.net.entity_type;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
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

    @NeverRemoval
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
