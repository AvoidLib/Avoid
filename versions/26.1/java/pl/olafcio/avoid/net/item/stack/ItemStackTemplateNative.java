package pl.olafcio.avoid.net.item.stack;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.ItemStackTemplate;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.item.ItemNative;
import pl.olafcio.avoid.net.item.component.map.EditableItemComponentMap;
import pl.olafcio.avoid.net.item.component.map.ItemComponentMapNative;

@Native
@ApiStatus.Internal
public final class ItemStackTemplateNative {
    @ApiStatus.Internal
    private ItemStackTemplateNative() {}

    public static ItemStackTemplate convert(pl.olafcio.avoid.net.item.stack.ItemStack item) {
        var map = (EditableItemComponentMap) item.getComponents();
        var map2 = ItemComponentMapNative.convert(map);

        return new ItemStackTemplate(Holder.direct(ItemNative.convert(item.getItem())), item.getAmount(), map2.asPatch());
    }

    public static pl.olafcio.avoid.net.item.stack.ItemStack convertFrom(ItemStackTemplate item) {
        var compmap = new PatchedDataComponentMap(item.item().components());
        var entries = item.components().entrySet();

        for (var entry : entries)
            setUnsafe(compmap, entry.getKey(), entry.getValue());

        return new pl.olafcio.avoid.net.item.stack.ItemStack(
                ItemNative.convert(item.item().value()),
                item.count(),
                ItemComponentMapNative.convertFrom(compmap)
        );
    }

    @SuppressWarnings("unchecked")
    private static <T> void setUnsafe(PatchedDataComponentMap map, DataComponentType<T> key, Object value) {
        map.set(key, (T) value);
    }
}
