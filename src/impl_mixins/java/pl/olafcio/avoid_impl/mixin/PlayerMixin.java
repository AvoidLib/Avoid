package pl.olafcio.avoid_impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.players.NameAndId;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid_impl.mixininterface.IEntity;
import pl.olafcio.avoid_impl.mixininterface.IFoodData;
import pl.olafcio.avoid_impl.mixininterface.INameAndId;

@Mixin(Player.class)
public class PlayerMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"), method = "updateIsUnderwater")
    protected boolean updateIsUnderwater__isEyeInFluid(Player player, TagKey tagKey, Operation<Boolean> original) {
        if (original.call(player, tagKey))
            return true;

        return ((IEntity) player).avoidlib$currentFluidSwimmable();
    }

    @WrapOperation(at = @At(value = "NEW", target = "()Lnet/minecraft/world/food/FoodData;"), method = "<init>")
    public FoodData $new__FoodData(Operation<FoodData> original) {
        var value = original.call();

        ((IFoodData) value).avoid$setPlayer((Player) (Object) this);

        return value;
    }

    @Inject(at = @At("RETURN"), method = "nameAndId")
    public void nameAndId(CallbackInfoReturnable<NameAndId> cir) {
        ((INameAndId) (Object) cir.getReturnValue()).avoid$owner(PlayerNative.convertFrom((Player) (Object) this));
    }
}
