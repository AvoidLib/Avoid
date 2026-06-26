package pl.olafcio.avoid.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.player_server.event.block.ServerPlayerCraftSlotEvent;
import pl.olafcio.avoid.net.world.WorldNative;

@Mixin(CraftingMenu.class)
public class CraftingMenuMixin {
    @ModifyVariable(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/ResultContainer;setItem(ILnet/minecraft/world/item/ItemStack;)V"
            ),
            method = "slotChangedCraftingGrid",
            name = "itemStack",
            index = 8
    )
    private static ItemStack itemstack(ItemStack value, AbstractContainerMenu abstractContainerMenu, ServerLevel serverLevel, Player player, CraftingContainer craftingContainer, ResultContainer resultContainer, @Nullable RecipeHolder<CraftingRecipe> recipeHolder) {
        var event = new ServerPlayerCraftSlotEvent(
                PlayerNative.convertFrom(player),
                WorldNative.make(serverLevel),
                ItemStackNative.convertFrom(value)
        );

        EventManager.fire(event);

        if (event.isModified())
            return ItemStackNative.convert(event.getItemStack());

        return value;
    }
}
