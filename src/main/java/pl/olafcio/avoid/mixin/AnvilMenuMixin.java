package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;
import pl.olafcio.avoid.net.player_server.event.block.anvil.ServerPlayerAnvilEnchantmentCompatibilityCheckEvent;
import pl.olafcio.avoid.net.player_server.event.block.anvil.ServerPlayerAnvilFailEvent;
import pl.olafcio.avoid.net.player_server.event.block.anvil.ServerPlayerAnvilNameValidationEvent;
import pl.olafcio.avoid.net.player_server.event.block.anvil.ServerPlayerAnvilSuccessResultEvent;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    public AnvilMenuMixin(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess, ItemCombinerMenuSlotDefinition itemCombinerMenuSlotDefinition) {
        super(menuType, i, inventory, containerLevelAccess, itemCombinerMenuSlotDefinition);
    }

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", ordinal = 0), method = "createResult")
    private boolean createResult__isEmpty(boolean value) {
        if (value) {
            var event = new ServerPlayerAnvilFailEvent(
                    ServerPlayerAnvilFailEvent.Reason.EMPTY,
                    ItemStackNative.convertFrom(this.inputSlots.getItem(0)),
                    ItemStackNative.convertFrom(this.inputSlots.getItem(1))
            );

            EventManager.fire(event);
        }

        return value;
    }

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;canStoreEnchantments(Lnet/minecraft/world/item/ItemStack;)Z"), method = "createResult")
    private boolean createResult__canStoreEnchantments(boolean value) {
        if (!value) {
            var event = new ServerPlayerAnvilFailEvent(
                    ServerPlayerAnvilFailEvent.Reason.UNENCHANTABLE,
                    ItemStackNative.convertFrom(this.inputSlots.getItem(0)),
                    ItemStackNative.convertFrom(this.inputSlots.getItem(1))
            );

            EventManager.fire(event);

            if (event.isCancelled())
                return true;
        }

        return value;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ResultContainer;setItem(ILnet/minecraft/world/item/ItemStack;)V", ordinal = 1), method = "createResult")
    private void createResult__cannotRepair(CallbackInfo ci) {
        var event = new ServerPlayerAnvilFailEvent(
                ServerPlayerAnvilFailEvent.Reason.NOT_ENOUGH_DURABILITY_TO_REPAIR,
                ItemStackNative.convertFrom(this.inputSlots.getItem(0)),
                ItemStackNative.convertFrom(this.inputSlots.getItem(1))
        );

        EventManager.fire(event);
    }

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 0), method = "createResult")
    private boolean createResult__isSameType(boolean value) {
        if (!value) {
            var event = new ServerPlayerAnvilFailEvent(
                    ServerPlayerAnvilFailEvent.Reason.NONBOOK_DIFFERENT,
                    ItemStackNative.convertFrom(this.inputSlots.getItem(0)),
                    ItemStackNative.convertFrom(this.inputSlots.getItem(1))
            );

            EventManager.fire(event);

            if (event.isCancelled())
                return true;
        }

        return value;
    }

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isDamageableItem()Z", ordinal = 1), method = "createResult")
    private boolean createResult__isDamageableItem(boolean value) {
        if (!value) {
            var event = new ServerPlayerAnvilFailEvent(
                    ServerPlayerAnvilFailEvent.Reason.NONBOOK_UNDAMAGEABLE,
                    ItemStackNative.convertFrom(this.inputSlots.getItem(0)),
                    ItemStackNative.convertFrom(this.inputSlots.getItem(1))
            );

            EventManager.fire(event);

            if (event.isCancelled())
                return true;
        }

        return value;
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;areCompatible(Lnet/minecraft/core/Holder;Lnet/minecraft/core/Holder;)Z", ordinal = 0), method = "createResult")
    private boolean createResult__compatibleEnchantment(Holder<Enchantment> holder, Holder<Enchantment> holder2, Operation<Boolean> original) {
        var value = original.call(holder, holder2);

        var event = new ServerPlayerAnvilEnchantmentCompatibilityCheckEvent(
                value,

                ItemStackNative.convertFrom(this.inputSlots.getItem(0)),
                ItemStackNative.convertFrom(this.inputSlots.getItem(1)),

                IdentificationNative.convertFrom(holder.unwrapKey().orElseThrow().identifier()),
                IdentificationNative.convertFrom(holder2.unwrapKey().orElseThrow().identifier())
        );

        EventManager.fire(event);

        if (event.isForced())
            return event.getForce();

        return value;
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Ljava/lang/String;length()I"), method = "validateName")
    private static int validateName__length(String instance, Operation<Integer> original) {
        var value = original.call(instance);

        var event = new ServerPlayerAnvilNameValidationEvent(
                value <= 50,
                instance
        );

        EventManager.fire(event);

        if (event.isForced())
            return event.willSucceed() ? 1 : 100;

        return value;
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ResultContainer;setItem(ILnet/minecraft/world/item/ItemStack;)V"), method = "createResult")
    private void createResult__setItem(ResultContainer instance, int i, ItemStack itemStack, Operation<Void> original) {
        var event = new ServerPlayerAnvilSuccessResultEvent(
                ItemStackNative.convertFrom(this.inputSlots.getItem(0)),
                ItemStackNative.convertFrom(this.inputSlots.getItem(1)),
                ItemStackNative.convertFrom(itemStack)
        );

        EventManager.fire(event);

        if (event.isModified())
            itemStack = ItemStackNative.convert(event.getResult());

        original.call(instance, i, itemStack);
    }
}
