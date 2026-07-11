package pl.olafcio.avoid.net.entity_type;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import java.util.HashMap;
import java.util.function.Supplier;

@Native
@ApiStatus.Internal
public final class EntityTypesNative {
    @ApiStatus.Internal
    private EntityTypesNative() {}

    public static HashMap<EntityType<? extends LivingEntity>, Supplier<AttributeSupplier>> getAndClean() {
        var val = EntityTypes.LIVING_ENTITIES;

        EntityTypes.LIVING_ENTITIES = null;

        return val;
    }
}
