package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import pl.olafcio.avoid.net.item.Item;
import pl.olafcio.avoid.net.item.ItemNative;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.Arrays;

public record Repairable(Item... items) {
    public static final class Controller
           implements TransformingItemComponentValue<net.minecraft.world.item.enchantment.Repairable, Repairable>
    {
        @Override
        public Repairable transform(net.minecraft.world.item.enchantment.Repairable value) {
            return new Repairable(value.items().stream()
                                               .map(Holder::value)
                                               .map(ItemNative::convert)
                                       .toArray(Item[]::new));
        }

        @Override
        public net.minecraft.world.item.enchantment.Repairable untransform(Repairable value) {
            return new net.minecraft.world.item.enchantment.Repairable(HolderSet.direct(Arrays.stream(value.items)
                                                                                              .map(ItemNative::convert)
                                                                                              .map(BuiltInRegistries.ITEM::wrapAsHolder)
                                                                                              .toList()));
        }
    }

    public static final _value_type<Repairable> TYPE
                  = new _value_type<>(net.minecraft.world.item.enchantment.Repairable.class, new Controller());
}
