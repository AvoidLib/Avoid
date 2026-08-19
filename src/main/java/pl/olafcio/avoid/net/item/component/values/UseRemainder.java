package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;

public record UseRemainder(ItemStack itemstack) {
    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.component.UseRemainder, UseRemainder>
    {
        @Override
        public UseRemainder transform(net.minecraft.world.item.component.UseRemainder value) {
            return new UseRemainder(ItemStackNative.convertFrom(value.convertInto()));
        }

        @Override
        public net.minecraft.world.item.component.UseRemainder untransform(UseRemainder value) {
            return new net.minecraft.world.item.component.UseRemainder(ItemStackNative.convert(value.itemstack));
        }
    }

    public static final _value_type<UseRemainder> TYPE
                  = new _value_type<>(net.minecraft.world.item.component.UseRemainder.class, new Controller());
}
