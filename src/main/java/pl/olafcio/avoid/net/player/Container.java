package pl.olafcio.avoid.net.player;

import net.minecraft.world.SimpleContainer;
import pl.olafcio.avoid.annotations.refactor.Discouraged;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;

public class Container {
    protected final Object inventory;

    Container(Object inventory) {
        this.inventory = inventory;
    }

    public void give(ItemStack itemStack) throws UnsupportedOperationException {
        if (inventory instanceof net.minecraft.world.entity.player.Inventory inv)
            inv.add(ItemStackNative.convert(itemStack));
        else if (inventory instanceof SimpleContainer inv)
            inv.addItem(ItemStackNative.convert(itemStack));
        else
            throw new UnsupportedOperationException("[Inventory#give] This container doesn't support this action");
    }

    public void clear() {
        ((net.minecraft.world.Container) inventory).clearContent();
    }

    public void setItem(int slot, ItemStack itemStack) {
        ((net.minecraft.world.Container) inventory).setItem(slot, ItemStackNative.convert(itemStack));
    }

    public ItemStack getItem(int slot) {
        return ItemStackNative.convertFrom(((net.minecraft.world.Container) inventory).getItem(slot));
    }

    @Discouraged(reason = "This method may get renamed.")
    public int getSize() {
        return ((net.minecraft.world.Container) inventory).getContainerSize();
    }
}
