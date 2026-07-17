package pl.olafcio.avoid.net.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.item.custom.AbstractItem;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;

@ApiStatus.Internal
public final class ItemNative {
    @ApiStatus.Internal
    private ItemNative() {}

    public static Item convert(pl.olafcio.avoid.net.item.Item item) {
        return item.item;
    }

    public static Item convert(pl.olafcio.avoid.net.item.custom.Item item) {
        return Items.CUSTOM_MAP.get(item);
    }

    public static pl.olafcio.avoid.net.item.Item convert(Item item) {
        return new pl.olafcio.avoid.net.item.Item(item);
    }

    public static Item convertFrom(AbstractItem anyItem) {
        if (anyItem instanceof pl.olafcio.avoid.net.item.custom.Item customItem)
            return convert(customItem);
        else if (anyItem instanceof pl.olafcio.avoid.net.item.Item naturalItem)
            return convert(naturalItem);
        else
            throw new RuntimeException("[Items::convert] Invalid 'anyItem' parameter");
    }

    public static Item make(pl.olafcio.avoid.net.item.custom.Item item, Item.Properties properties) {
        // TODO: Add some Item methods
        return new Item(properties) {
            @Override
            @NotNull
            public ItemStack getDefaultInstance() {
                var stack = super.getDefaultInstance();
                var converted = ItemStackNative.convertFrom(stack);

                converted = item.newStack(converted);

                return ItemStackNative.convert(converted);
            }
        };
    }
}
