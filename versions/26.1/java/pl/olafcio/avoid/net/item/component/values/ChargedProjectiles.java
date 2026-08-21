package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.item.stack.ItemStackTemplateNative;

import java.util.List;

public record ChargedProjectiles(List<ItemStack> itemstacks) {
    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.component.ChargedProjectiles, ChargedProjectiles>
    {
        @Override
        public ChargedProjectiles transform(net.minecraft.world.item.component.ChargedProjectiles value) {
            return new ChargedProjectiles(value.items().stream().map(ItemStackTemplateNative::convertFrom).toList());
        }

        @Override
        public net.minecraft.world.item.component.ChargedProjectiles untransform(ChargedProjectiles value) {
            return new net.minecraft.world.item.component.ChargedProjectiles(value.itemstacks.stream().map(ItemStackTemplateNative::convert).toList());
        }
    }

    public static final _value_type<ChargedProjectiles> TYPE
                  = new _value_type<>(net.minecraft.world.item.component.ChargedProjectiles.class, new Controller());
}
