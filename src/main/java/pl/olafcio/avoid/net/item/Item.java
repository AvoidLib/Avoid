package pl.olafcio.avoid.net.item;

import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.map.ItemComponentMap;
import pl.olafcio.avoid.net.item.custom.AbstractItem;

@NeverRemoval
public final class Item extends AbstractItem {
    final net.minecraft.world.item.Item item;

    Item(net.minecraft.world.item.Item item) {
        this.item = item;
    }

    @NeverRemoval
    public BaseComponent<?> getName() {
        return COFromNative.from(item.getName());
    }

    @ApiStatus.Experimental
    public Identification getID() {
        return IdentificationNative.convertFrom(BuiltInRegistries.ITEM.getKey(item));
    }

    @NeverRemoval
    public ItemComponentMap getComponents() {
        return new ItemComponentMap(item.components());
    }

    @NeverRemoval
    public String toString() {
        return BuiltInRegistries.ITEM.wrapAsHolder(item).getRegisteredName();
    }

    @NeverRemoval
    public static Item of(Identification id) {
        var mcItem = BuiltInRegistries.ITEM.getValue(IdentificationNative.convert(id));
        return new Item(mcItem);
    }

    @NeverRemoval
    public static Item of(String id) {
        var mcItem = BuiltInRegistries.ITEM.getValue(IdentificationNative.convert(id));
        return new Item(mcItem);
    }
}
