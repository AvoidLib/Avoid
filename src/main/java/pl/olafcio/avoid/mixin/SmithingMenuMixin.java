package pl.olafcio.avoid.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.player_server.event.block.ServerPlayerSmithingSlotEvent;
import pl.olafcio.avoid.net.world.WorldNative;

import java.util.Optional;

@Mixin(SmithingMenu.class)
public abstract class SmithingMenuMixin extends ItemCombinerMenu {
    @Shadow protected abstract SmithingRecipeInput createRecipeInput();

    @Shadow @Final private Level level;

    public SmithingMenuMixin(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess, ItemCombinerMenuSlotDefinition itemCombinerMenuSlotDefinition) {
        super(menuType, i, inventory, containerLevelAccess, itemCombinerMenuSlotDefinition);
    }

    @Inject(at = @At("HEAD"), method = "createResult", cancellable = true)
    private void createResult(CallbackInfo ci) {
        ci.cancel();

        var smithingRecipeInput = this.createRecipeInput();
        var optional =
                (this.level instanceof ServerLevel serverLevel)
                        ? serverLevel.recipeAccess().getRecipeFor(RecipeType.SMITHING, smithingRecipeInput, serverLevel)
                        : Optional.<RecipeHolder<?>>empty();

        optional.ifPresentOrElse((recipeHolder) -> {
            ItemStack itemStack = ((SmithingRecipe) recipeHolder.value()).assemble(smithingRecipeInput, this.level.registryAccess());

            var event = new ServerPlayerSmithingSlotEvent(
                    PlayerNative.convertFrom(player),
                    WorldNative.make(level),
                    ItemStackNative.convertFrom(itemStack)
            );

            EventManager.fire(event);

            if (event.isModified())
                itemStack = ItemStackNative.convert(event.getItemStack());

            this.resultSlots.setRecipeUsed(recipeHolder);
            this.resultSlots.setItem(0, itemStack);
        }, () -> {
            var itemStack = ItemStack.EMPTY;

            var event = new ServerPlayerSmithingSlotEvent(
                    PlayerNative.convertFrom(player),
                    WorldNative.make(level),
                    ItemStackNative.convertFrom(itemStack)
            );

            EventManager.fire(event);

            if (event.isModified())
                itemStack = ItemStackNative.convert(event.getItemStack());

            this.resultSlots.setRecipeUsed((RecipeHolder<?>) null);
            this.resultSlots.setItem(0, itemStack);
        });
    }
}
