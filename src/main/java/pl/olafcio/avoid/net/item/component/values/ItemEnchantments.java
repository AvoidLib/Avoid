package pl.olafcio.avoid.net.item.component.values;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.internal.VResourceKey;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.HashMap;

public record ItemEnchantments(HashMap<Identification, Integer> map) {
    private static final HolderLookup.RegistryLookup<Enchantment> ench
                       = AvoidInternal.registry.lookup(Registries.ENCHANTMENT).orElseThrow();

    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.enchantment.ItemEnchantments, ItemEnchantments>
    {
        @Override
        public ItemEnchantments transform(net.minecraft.world.item.enchantment.ItemEnchantments value) {
            var map = new HashMap<Identification, Integer>();
            var entries = value.entrySet();

            for (var entry : entries)
                map.put(
                        IdentificationNative.convertFrom(VResourceKey.identifier(entry.getKey().unwrapKey().orElseThrow())),
                        entry.getIntValue()
                );

            return new ItemEnchantments(map);
        }

        @Override
        public net.minecraft.world.item.enchantment.ItemEnchantments untransform(ItemEnchantments value) {
            var map = new Object2IntOpenHashMap<Holder<Enchantment>>();
            var entries = value.map.entrySet();

            for (var entry : entries)
                map.put(
                        ench.listElements().filter(e -> {
                            var key = e.key();
                            return VResourceKey.identifier(key).equals(IdentificationNative.convert(entry.getKey()));
                        }).findAny().orElseThrow(),

                        entry.getValue()
                );

            return new net.minecraft.world.item.enchantment.ItemEnchantments(map);
        }
    }

    public static final _value_type<ItemEnchantments> TYPE
                  = new _value_type<>(net.minecraft.world.item.enchantment.ItemEnchantments.class, new Controller());
}
