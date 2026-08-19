package pl.olafcio.avoid.mixin.accessors;

import net.minecraft.world.entity.animal.fish.TropicalFish;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TropicalFish.Base.class)
public interface ITropicalFish$Base {
    @Accessor("id")
    int avoid$id();
}
