package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;

import java.util.List;

public record ItemContainerContents(List<ItemStack> itemstacks) {
    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.component.ItemContainerContents, ItemContainerContents>
    {
        @Override
        public ItemContainerContents transform(net.minecraft.world.item.component.ItemContainerContents value) {
            return new ItemContainerContents(value.stream().map(ItemStackNative::convertFrom).toList());
        }

        @Override
        public net.minecraft.world.item.component.ItemContainerContents untransform(ItemContainerContents value) {
            return net.minecraft.world.item.component.ItemContainerContents.fromItems(value.itemstacks.stream().map(ItemStackNative::convert).toList());
        }
    }

    public static final _value_type<ItemContainerContents> TYPE
                  = new _value_type<>(net.minecraft.world.item.component.ItemContainerContents.class, new Controller());
}
