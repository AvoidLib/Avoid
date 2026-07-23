package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid.mixininterface.IEntity;

@Mixin(Player.class)
public class PlayerMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"), method = "updateIsUnderwater")
    protected boolean updateIsUnderwater__isEyeInFluid(Player player, TagKey tagKey, Operation<Boolean> original) {
        if (original.call(player, tagKey))
            return true;

        return ((IEntity) player).avoidlib$currentFluidSwimmable();
    }
}
