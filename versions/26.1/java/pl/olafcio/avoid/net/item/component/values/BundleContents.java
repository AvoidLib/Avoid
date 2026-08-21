package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.item.stack.ItemStackTemplateNative;

import java.util.List;

public record BundleContents(List<ItemStack> itemstacks) {
    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.component.BundleContents, BundleContents>
    {
        @Override
        public BundleContents transform(net.minecraft.world.item.component.BundleContents value) {
            return new BundleContents(value.items().stream().map(ItemStackTemplateNative::convertFrom).toList());
        }

        @Override
        public net.minecraft.world.item.component.BundleContents untransform(BundleContents value) {
            return new net.minecraft.world.item.component.BundleContents(value.itemstacks.stream().map(ItemStackTemplateNative::convert).toList());
        }
    }

    public static final _value_type<BundleContents> TYPE
                  = new _value_type<>(net.minecraft.world.item.component.BundleContents.class, new Controller());
}
