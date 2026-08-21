package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.core.registries.BuiltInRegistries;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.internal.VResourceKey;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.Optional;

public record PiercingWeapon(
        boolean dealsKnockback,
        boolean dismounts,
        @Nullable Identification sound,
        @Nullable Identification hitSound
) {
    @SuppressWarnings("NullableProblems")
    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.component.PiercingWeapon, PiercingWeapon>
    {
        @Override
        public PiercingWeapon transform(net.minecraft.world.item.component.PiercingWeapon value) {
            return new PiercingWeapon(
                    value.dealsKnockback(),
                    value.dismounts(),
                    value.sound().isEmpty() ? null : IdentificationNative.convertFrom(VResourceKey.identifier(value.sound().orElseThrow().unwrapKey().orElseThrow())),
                    value.hitSound().isEmpty() ? null : IdentificationNative.convertFrom(VResourceKey.identifier(value.hitSound().orElseThrow().unwrapKey().orElseThrow()))
            );
        }

        @Override
        public net.minecraft.world.item.component.PiercingWeapon untransform(PiercingWeapon value) {
            return new net.minecraft.world.item.component.PiercingWeapon(
                    value.dealsKnockback,
                    value.dismounts,
                    value.sound == null ? Optional.empty() : Optional.of(BuiltInRegistries.SOUND_EVENT.get(IdentificationNative.convert(value.sound)).orElseThrow()),
                    value.hitSound == null ? Optional.empty() : Optional.of(BuiltInRegistries.SOUND_EVENT.get(IdentificationNative.convert(value.hitSound)).orElseThrow())
            );
        }
    }

    public static final _value_type<PiercingWeapon> TYPE
                  = new _value_type<>(net.minecraft.world.item.component.PiercingWeapon.class, new Controller());
}
