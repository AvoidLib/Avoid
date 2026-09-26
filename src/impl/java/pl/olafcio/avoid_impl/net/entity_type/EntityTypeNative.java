package pl.olafcio.avoid_impl.net.entity_type;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class EntityTypeNative {
    @ApiStatus.Internal
    private EntityTypeNative() {}

    public static net.minecraft.world.entity.EntityType<?> convert(pl.olafcio.avoid.net.entity_type.EntityType entityType) {
        return ((EntityType) entityType).entityType;
    }

    public static pl.olafcio.avoid.net.entity_type.EntityType convertFrom(net.minecraft.world.entity.EntityType<?> entityType) {
        return new EntityType(entityType);
    }
}
