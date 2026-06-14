package pl.olafcio.avoid.net.item;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class ItemNative {
    @ApiStatus.Internal
    private ItemNative() {}

    public static Item convert(pl.olafcio.avoid.net.item.Item item) {
        return item.item;
    }

    public static pl.olafcio.avoid.net.item.Item convert(Item item) {
        return new pl.olafcio.avoid.net.item.Item(item);
    }

    public static Item make(pl.olafcio.avoid.net.item.custom.Item item, Item.Properties properties) {
        // TODO: Add some Item methods
        return new Item(properties) {};
    }
}
