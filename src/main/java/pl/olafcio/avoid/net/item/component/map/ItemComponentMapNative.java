package pl.olafcio.avoid.net.item.component.map;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.item.component.ItemComponentNative;

@Native
@NullMarked
@ApiStatus.Internal
public final class ItemComponentMapNative {
    @ApiStatus.Internal
    private ItemComponentMapNative() {}

    public static PatchedDataComponentMap convert(EditableItemComponentMap input) {
        var map = new PatchedDataComponentMap(input.map);
        var patch = input.patch;

        for (var element : patch.entrySet()) {
            var key = ItemComponentNative.convert(element.getKey());
            var value = element.getValue();

            setUnsafe(map, key.orElseThrow(), value);
        }

        return map;
    }

    @SuppressWarnings("unchecked")
    private static <T> void setUnsafe(PatchedDataComponentMap map, DataComponentType<?> key, Object object) {
        map.set((DataComponentType<T>) key, (T) object);
    }

    public static ItemComponentMap convertFrom(DataComponentMap input) {
        return new ItemComponentMap(input);
    }
}
