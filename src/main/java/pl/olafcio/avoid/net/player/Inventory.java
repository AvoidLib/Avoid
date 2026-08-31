package pl.olafcio.avoid.net.player;

import net.minecraft.world.SimpleContainer;
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
public class Inventory extends pl.olafcio.avoid.net.player.Container {
    Inventory(Object inventory) {
        super(inventory);
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

    public void clear(ItemStack itemStack) {
        ((net.minecraft.world.entity.player.Inventory) inventory).removeItem(ItemStackNative.convert(itemStack));
    }

    public boolean contains(ItemStack itemStack) {
        return ((net.minecraft.world.entity.player.Inventory) inventory).contains(ItemStackNative.convert(itemStack));
    }

    @Discouraged(reason = "This method may get renamed.")
    public void addItem(int slot, ItemStack itemStack) {
        ((net.minecraft.world.entity.player.Inventory) inventory).add(slot, ItemStackNative.convert(itemStack));
    }

    @Discouraged(reason = "There's a high chance this might be refactored away to another object in a future release.")
    public ItemStack getHotbarItem() {
        return ItemStackNative.convertFrom(((net.minecraft.world.entity.player.Inventory) inventory).getSelectedItem());
    }
}
