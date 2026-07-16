package pl.olafcio.avoid.net.item.custom;

import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.item.Items;
import pl.olafcio.avoid.net.item.stack.ItemStack;

public abstract class Item extends AbstractItem {
    public ItemStack newStack(ItemStack stack) {
        return stack;
    }

    @Override
    public final BaseComponent<?> getName() {
        return Items.getName(this);
    }

    @Override
    public final Identification getID() {
        return Items.getID(this);
    }

    @Override
    public final String toString() {
        return "Item{%s}".formatted(getID());
    }
}
