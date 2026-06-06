package pl.olafcio.avoid.net.item;

import net.minecraft.core.registries.BuiltInRegistries;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.map.ItemComponentMap;

@SuppressWarnings("ClassCanBeRecord")
public final class Item {
    final net.minecraft.world.item.Item item;

    Item(net.minecraft.world.item.Item item) {
        this.item = item;
    }

    public BaseComponent<?> getName() {
        return COFromNative.from(item.getName());
    }

    public ItemComponentMap getComponents() {
        return new ItemComponentMap(item.components());
    }

    public String toString() {
        return BuiltInRegistries.ITEM.wrapAsHolder(item).getRegisteredName();
    }

    public static Item of(Identification id) {
        var mcItem = BuiltInRegistries.ITEM.getValue(IdentificationNative.convert(id));
        return new Item(mcItem);
    }

    public static Item of(String id) {
        var mcItem = BuiltInRegistries.ITEM.getValue(IdentificationNative.convert(id));
        return new Item(mcItem);
    }
}
