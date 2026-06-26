package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.player_server.event.block.enchanting_table.ServerPlayerEnchantingTableBookshelfLimitEvent;
import pl.olafcio.avoid.net.player_server.event.block.enchanting_table.ServerPlayerEnchantingTableCostEvent;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @ModifyConstant(constant = {
            @Constant(intValue = 15)
    }, method = "getEnchantmentCost")
    private static int getEnchantmentCost__bookshelfLimit(int constant) {
        var event = new ServerPlayerEnchantingTableBookshelfLimitEvent(constant);

        EventManager.fire(event);

        if (event.isCancelled())
            return Integer.MAX_VALUE;

        return event.getLimit();
    }

    @ModifyReturnValue(at = @At("RETURN"), method = "getEnchantmentCost")
    private static int getEnchantmentCost(int original) {
        var event = new ServerPlayerEnchantingTableCostEvent(original);

        EventManager.fire(event);

        return event.getCost();
    }
}
