package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid.mixininterface.IEntityFluidInteraction;
import pl.olafcio.avoid.mixininterface.IFoodData;
import pl.olafcio.avoid.net.fluid.FluidsNative;
import pl.olafcio.avoid.net.fluid.properties._swimmable;

@Mixin(Player.class)
public class PlayerMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"), method = "updateIsUnderwater")
    protected boolean updateIsUnderwater__isEyeInFluid(Player player, TagKey tagKey, Operation<Boolean> original) {
        if (original.call(player, tagKey))
            return true;

        var interaction = (IEntityFluidInteraction) player.fluidInteraction;

        for (var fluid : FluidsNative.instances.keySet())
            if (interaction.avoid$isEyeInFluid(fluid))
                if (fluid.getClass().isAnnotationPresent(_swimmable.class))
                    return true;

        return false;
    }

    @WrapOperation(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodData;"), method = "<init>")
    public FoodData $new__FoodData(Operation<FoodData> original) {
        var value = original.call();

        ((IFoodData) value).avoid$setPlayer((Player) (Object) this);

        return value;
    }
}
