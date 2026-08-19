package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;

import java.util.List;

public record BundleContents(List<ItemStack> itemstacks) {
    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.component.BundleContents, BundleContents>
    {
        @Override
        public BundleContents transform(net.minecraft.world.item.component.BundleContents value) {
            return new BundleContents(((List<net.minecraft.world.item.ItemStack>) value.items()).stream().map(ItemStackNative::convertFrom).toList());
        }

        @Override
        public net.minecraft.world.item.component.BundleContents untransform(BundleContents value) {
            return new net.minecraft.world.item.component.BundleContents(value.itemstacks.stream().map(ItemStackNative::convert).toList());
        }
    }

    public static final _value_type<BundleContents> TYPE
                  = new _value_type<>(net.minecraft.world.item.component.BundleContents.class, new Controller());
}
