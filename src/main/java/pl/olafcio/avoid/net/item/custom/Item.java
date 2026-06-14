package pl.olafcio.avoid.net.item.custom;

import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.item.Items;

public abstract class Item extends AbstractItem {
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
        return "Block{%s}".formatted(getID());
    }
}
