package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.internal.VResourceKey;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public record ChickenVariant(Identification id) {
    private static final HolderLookup.RegistryLookup<net.minecraft.world.entity.animal.chicken.ChickenVariant> chick
                       = AvoidInternal.registry.lookup(Registries.CHICKEN_VARIANT).orElseThrow();

    @SuppressWarnings("NullableProblems")
    public static final class Controller
            implements TransformingItemComponentValue<Holder<net.minecraft.world.entity.animal.chicken.ChickenVariant>, ChickenVariant>
    {
        @Override
        public ChickenVariant transform(Holder<net.minecraft.world.entity.animal.chicken.ChickenVariant> value) {
            return new ChickenVariant(IdentificationNative.convertFrom(VResourceKey.identifier(value.unwrapKey().orElseThrow())));
        }

        @Override
        public Holder<net.minecraft.world.entity.animal.chicken.ChickenVariant> untransform(ChickenVariant value) {
            return chick.getOrThrow(ResourceKey.create(Registries.CHICKEN_VARIANT, IdentificationNative.convert(value.id)));
        }
    }

    public static final _value_type<ChickenVariant> TYPE
                  = new _value_type<>(
                          net.minecraft.world.entity.animal.chicken.ChickenVariant.CODEC,
                          net.minecraft.world.entity.animal.chicken.ChickenVariant.STREAM_CODEC,
                          new Controller()
                    );
}
