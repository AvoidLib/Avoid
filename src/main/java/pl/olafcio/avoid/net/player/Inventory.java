package pl.olafcio.avoid.net.player;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.Discouraged;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;

/**
 * A player's inventory.
 * <br/><br/>
 * There are a few concepts regarding inventories.<br/>
 * Here's a list of them:
 * <br/><br/>
 * <h2>Slots</h2>
 * A slot is a place for an item.<br/>
 * It can be empty.
 * <br/><br/>
 * <h2>Items</h2>
 * An item is a type of item, e.g. `minecraft:dirt`.
 * <br/><br/>
 * <h2>Item stacks</h2>
 * An item stack is an instance of an item.<br/>
 * It is unique, and additionally contains:
 * <ul>
 *     <li>the amount (typically 1-64),</li>
 *     <li>the NBT (item data).</li>
 * </ul>
 */
@ApiStatus.Experimental
public class Inventory {
    private final Object inventory;

    Inventory(Object inventory) {
        this.inventory = inventory;
    }

    /**
     * Finds the first empty slot in the inventory.
     */
    public int getFreeSlot() {
        return ((net.minecraft.world.entity.player.Inventory) inventory).getFreeSlot();
    }

    @Discouraged(reason = "There's a high chance this might be refactored away to another object in a future release.")
    public int getHotbarSlot() {
        return ((net.minecraft.world.entity.player.Inventory) inventory).getSelectedSlot();
    }

    @Discouraged(reason = "There's a high chance this might be refactored away to another object in a future release.")
    public int getSuitableHotbarSlot() {
        return ((net.minecraft.world.entity.player.Inventory) inventory).getSuitableHotbarSlot();
    }

    public void give(ItemStack itemStack) {
        ((net.minecraft.world.entity.player.Inventory) inventory).add(ItemStackNative.convert(itemStack));
    }

    public void clear(ItemStack itemStack) {
        ((net.minecraft.world.entity.player.Inventory) inventory).removeItem(ItemStackNative.convert(itemStack));
    }

    public void clear() {
        ((net.minecraft.world.entity.player.Inventory) inventory).clearContent();
    }

    public boolean contains(ItemStack itemStack) {
        return ((net.minecraft.world.entity.player.Inventory) inventory).contains(ItemStackNative.convert(itemStack));
    }

    @Discouraged(reason = "This method may get renamed.")
    public void addItem(int slot, ItemStack itemStack) {
        ((net.minecraft.world.entity.player.Inventory) inventory).add(slot, ItemStackNative.convert(itemStack));
    }

    public void setItem(int slot, ItemStack itemStack) {
        ((net.minecraft.world.entity.player.Inventory) inventory).setItem(slot, ItemStackNative.convert(itemStack));
    }

    public ItemStack getItem(int slot) {
        return ItemStackNative.convertFrom(((net.minecraft.world.entity.player.Inventory) inventory).getItem(slot));
    }

    @Discouraged(reason = "There's a high chance this might be refactored away to another object in a future release.")
    public ItemStack getHotbarItem() {
        return ItemStackNative.convertFrom(((net.minecraft.world.entity.player.Inventory) inventory).getSelectedItem());
    }

    @Discouraged(reason = "This method may get renamed.")
    public int getSize() {
        return ((net.minecraft.world.entity.player.Inventory) inventory).getContainerSize();
    }
}
