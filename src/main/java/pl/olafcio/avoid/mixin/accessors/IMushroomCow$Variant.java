package pl.olafcio.avoid.mixin.accessors;

import net.minecraft.world.entity.animal.cow.MushroomCow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MushroomCow.Variant.class)
public interface IMushroomCow$Variant {
    @Invoker("id")
    int avoid$id();
}
