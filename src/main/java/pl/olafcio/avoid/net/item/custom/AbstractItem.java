package pl.olafcio.avoid.net.item.custom;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.item.Item;

@ApiStatus.Experimental
public abstract class AbstractItem {
    @ApiStatus.Internal
    protected AbstractItem() {
        if (this.getClass() != Item.class && !(this instanceof pl.olafcio.avoid.net.item.custom.Item))
            throw new UnsupportedOperationException("AbstractItem can be only extended by internal AvoidLib classes; " +
                                                    "if you wanted to create a custom item, extend Item (avoid.net.item.custom)");
    }

    public abstract BaseComponent<?> getName();
    public abstract Identification getID();
    public abstract String toString();
}
