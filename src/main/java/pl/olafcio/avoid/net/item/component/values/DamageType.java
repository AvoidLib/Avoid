package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.EitherHolder;
import pl.olafcio.avoid.internal.VResourceKey;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public record DamageType(Identification id) {
    public static final class Controller
            implements TransformingItemComponentValue<EitherHolder<net.minecraft.world.damagesource.DamageType>, DamageType>
    {
        @Override
        public DamageType transform(EitherHolder<net.minecraft.world.damagesource.DamageType> value) {
            return new DamageType(IdentificationNative.convertFrom(VResourceKey.identifier(value.key().orElseThrow())));
        }

        @Override
        public EitherHolder<net.minecraft.world.damagesource.DamageType> untransform(DamageType value) {
            return new EitherHolder<>(ResourceKey.create(Registries.DAMAGE_TYPE, IdentificationNative.convert(value.id)));
        }
    }

    public static final _value_type<DamageType> TYPE
                  = new _value_type<>(EitherHolder.class, new Controller());
}
