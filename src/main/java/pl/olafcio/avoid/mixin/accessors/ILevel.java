package pl.olafcio.avoid.mixin.accessors;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.LevelEntityGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Level.class)
public interface ILevel {
    @Invoker("getEntities")
    LevelEntityGetter<Entity> avoid$getEntities();
}
