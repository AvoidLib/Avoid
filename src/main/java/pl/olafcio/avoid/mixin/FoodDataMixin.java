package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid.mixininterface.IFoodData;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.player_server.event.food.ServerPlayerSetFoodLevelEvent;
import pl.olafcio.avoid.net.player_server.event.food.ServerPlayerSetSaturationLevelEvent;

@Mixin(FoodData.class)
public class FoodDataMixin implements IFoodData {
    @Unique
    private Player player;

    @Override
    public void avoid$setPlayer(Player player) {
        this.player = player;
    }

    @Shadow
    private int foodLevel;

    @WrapOperation(at = @At(value = "FIELD", target = "Lnet/minecraft/world/food/FoodData;foodLevel:I", opcode = Opcodes.PUTFIELD), method = {"add", "tick", "setFoodLevel"})
    public void $modify__foodLevel(FoodData instance, int value, Operation<Void> original) {
        if (!player.level().isClientSide()) {
            var event = new ServerPlayerSetFoodLevelEvent(
                    PlayerNative.convertFrom(player),
                    value,
                    this.foodLevel
            );

            EventManager.fire(event);

            if (event.isCancelled())
                return;
            else if (event.isLevelChanged())
                value = event.getLevel();
        }

        original.call(instance, value);
    }

    @WrapOperation(at = @At(value = "FIELD", target = "Lnet/minecraft/world/food/FoodData;saturationLevel:F", opcode = Opcodes.PUTFIELD), method = {"add", "tick", "setFoodLevel"})
    public void $modify__saturationLevel(FoodData instance, float value, Operation<Void> original) {
        if (!player.level().isClientSide()) {
            var event = new ServerPlayerSetSaturationLevelEvent(
                    PlayerNative.convertFrom(player),
                    value,
                    this.foodLevel
            );

            EventManager.fire(event);

            if (event.isCancelled())
                return;
            else if (event.isLevelChanged())
                value = event.getLevel();
        }

        original.call(instance, value);
    }
}
