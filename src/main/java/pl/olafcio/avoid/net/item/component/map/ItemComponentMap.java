package pl.olafcio.avoid.net.item.component.map;

import net.minecraft.core.component.DataComponentMap;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.item.component.ItemComponentType;
import pl.olafcio.avoid.net.item.component.ItemComponentNative;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentType;

public sealed class ItemComponentMap
              permits EditableItemComponentMap
{
    final DataComponentMap map;

    @ApiStatus.Internal
    public ItemComponentMap(DataComponentMap underlyingMap) {
        this.map = underlyingMap;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(ItemComponentType<T> type) {
        var value = map.get(ItemComponentNative.convert(type)
                       .orElseThrow());

        if (type instanceof TransformingItemComponentType<?,?> transformingKey)
            return (T) transformUnsafe(transformingKey, value);

        return (T) value;
    }

    @SuppressWarnings("unchecked")
    private static <I, O> O transformUnsafe(TransformingItemComponentType<I, O> transformingKey, Object value) {
        return transformingKey.transform((I) value);
    }
}
