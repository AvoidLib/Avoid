package pl.olafcio.avoid.mixin.accessors;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityType.class)
public interface IEntityType<T extends Entity> {
    @Accessor("factory")
    EntityType.EntityFactory<T> avoid$factory();
}
