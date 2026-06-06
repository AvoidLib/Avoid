package pl.olafcio.avoid.net.item.component.map;

import net.minecraft.core.component.DataComponentMap;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.item.component.ItemComponentType;
import pl.olafcio.avoid.net.item.component.ItemComponentNative;

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
        return (T) map.get(ItemComponentNative.convert(type)
                                              .orElseThrow());
    }
}
