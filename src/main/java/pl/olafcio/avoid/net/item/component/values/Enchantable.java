package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public record Enchantable(int value) {
    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.enchantment.Enchantable, Enchantable>
    {
        @Override
        public Enchantable transform(net.minecraft.world.item.enchantment.Enchantable value) {
            return new Enchantable(value.value());
        }

        @Override
        public net.minecraft.world.item.enchantment.Enchantable untransform(Enchantable value) {
            return new net.minecraft.world.item.enchantment.Enchantable(value.value);
        }
    }
}
