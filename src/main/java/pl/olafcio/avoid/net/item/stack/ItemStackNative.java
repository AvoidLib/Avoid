package pl.olafcio.avoid.net.item.stack;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.item.ItemNative;
import pl.olafcio.avoid.net.item.component.map.EditableItemComponentMap;
import pl.olafcio.avoid.net.item.component.map.ItemComponentMapNative;

@Native
@ApiStatus.Internal
public final class ItemStackNative {
    @ApiStatus.Internal
    private ItemStackNative() {}

    public static ItemStack convert(pl.olafcio.avoid.net.item.stack.ItemStack item) {
        var map = (EditableItemComponentMap) item.getComponents();
        var map2 = ItemComponentMapNative.convert(map);

        return new ItemStack(ItemNative.convert(item.getItem()), item.getAmount(), map2);
    }

    public static pl.olafcio.avoid.net.item.stack.ItemStack convertFrom(ItemStack item) {
        return new pl.olafcio.avoid.net.item.stack.ItemStack(
                ItemNative.convert(item.getItem()),
                item.getCount(),
                ItemComponentMapNative.convertFrom(item.getComponents())
        );
    }
}
