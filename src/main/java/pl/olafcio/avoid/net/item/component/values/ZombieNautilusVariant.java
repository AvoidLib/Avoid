package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.EitherHolder;
import pl.olafcio.avoid.internal.VResourceKey;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public record ZombieNautilusVariant(Identification id) {
    @SuppressWarnings("NullableProblems")
    public static final class Controller
            implements TransformingItemComponentValue<EitherHolder<net.minecraft.world.entity.animal.nautilus.ZombieNautilusVariant>, ZombieNautilusVariant>
    {
        @Override
        public ZombieNautilusVariant transform(EitherHolder<net.minecraft.world.entity.animal.nautilus.ZombieNautilusVariant> value) {
            return new ZombieNautilusVariant(IdentificationNative.convertFrom(VResourceKey.identifier(value.key().orElseThrow())));
        }

        @Override
        public EitherHolder<net.minecraft.world.entity.animal.nautilus.ZombieNautilusVariant> untransform(ZombieNautilusVariant value) {
            return new EitherHolder<>(ResourceKey.create(Registries.ZOMBIE_NAUTILUS_VARIANT, IdentificationNative.convert(value.id)));
        }
    }

    public static final _value_type<ZombieNautilusVariant> TYPE
                  = new _value_type<>(
                          net.minecraft.world.entity.animal.nautilus.ZombieNautilusVariant.CODEC,
                          net.minecraft.world.entity.animal.nautilus.ZombieNautilusVariant.STREAM_CODEC,
                          new Controller()
                    );
}
