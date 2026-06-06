package pl.olafcio.avoid.net.entity_type;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class EntityTypeNative {
    @ApiStatus.Internal
    private EntityTypeNative() {}

    public static net.minecraft.world.entity.EntityType<?> convert(EntityType entityType) {
        return entityType.entityType;
    }

    public static EntityType convertFrom(net.minecraft.world.entity.EntityType<?> entityType) {
        return new EntityType(entityType);
    }
}
