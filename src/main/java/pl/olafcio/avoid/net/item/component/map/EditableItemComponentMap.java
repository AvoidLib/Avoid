package pl.olafcio.avoid.net.item.component.map;

import net.minecraft.core.component.DataComponentMap;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.item.component.ItemComponentType;

import java.util.HashMap;

public final class EditableItemComponentMap
             extends ItemComponentMap
{
    final HashMap<ItemComponentType<?>, Object> patch;

    @ApiStatus.Internal
    public EditableItemComponentMap(DataComponentMap underlyingMap) {
        super(underlyingMap);
        this.patch = new HashMap<>();
    }

    public EditableItemComponentMap(ItemComponentMap components) {
        super(components.map);
        this.patch = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    private EditableItemComponentMap(DataComponentMap underlyingMap, HashMap<ItemComponentType<?>, Object> patch) {
        super(underlyingMap);
        this.patch = (HashMap<ItemComponentType<?>, Object>) patch.clone();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(ItemComponentType<T> type) {
        return (T) patch.getOrDefault(type, super.get(type));
    }

    public <T> void set(ItemComponentType<T> type, T value) {
        patch.put(type, value);
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public EditableItemComponentMap clone() {
        return new EditableItemComponentMap(map, patch);
    }
}
