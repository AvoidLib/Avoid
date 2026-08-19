package pl.olafcio.avoid.mixin.accessors;

import net.minecraft.world.entity.animal.fish.Salmon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Salmon.Variant.class)
public interface ISalmon$Variant {
    @Invoker("id")
    int avoid$id();
}
