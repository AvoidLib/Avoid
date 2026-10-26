package pl.olafcio.avoid_impl.mixin;

import org.spongepowered.asm.mixin.Unique;
import pl.olafcio.avoid_impl.mixininterface.XEntityType;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import pl.olafcio.avoid_impl.net.entity_type.AvoidEntityType;

@Mixin(EntityType.class)
public class EntityTypeMixin implements XEntityType {
    @Unique
    AvoidEntityType aet;

    @Override
    public AvoidEntityType avoid$entityType() {
        return aet;
    }

    @Override
    public void avoid$entityType(AvoidEntityType value) {
        aet = value;
    }
}
