package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.core.registries.BuiltInRegistries;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.internal.VResourceKey;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.Optional;

public record KineticWeapon(
        int contactCooldownTicks,
        int delayTicks,
        @Nullable KineticWeaponCondition dismountConditions,
        @Nullable KineticWeaponCondition knockbackConditions,
        @Nullable KineticWeaponCondition damageConditions,
        float forwardMovement,
        float damageMultiplier,
        @Nullable Identification sound,
        @Nullable Identification hitSound
) {
    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.component.KineticWeapon, KineticWeapon>
    {
        @Override
        public KineticWeapon transform(net.minecraft.world.item.component.KineticWeapon value) {
            return new KineticWeapon(
                    value.contactCooldownTicks(),
                    value.delayTicks(),
                    value.dismountConditions().isEmpty() ? null : KineticWeaponCondition.convert(value.dismountConditions().get()),
                    value.knockbackConditions().isEmpty() ? null : KineticWeaponCondition.convert(value.knockbackConditions().get()),
                    value.damageConditions().isEmpty() ? null : KineticWeaponCondition.convert(value.damageConditions().get()),
                    value.forwardMovement(),
                    value.damageMultiplier(),
                    value.sound().isEmpty() ? null : IdentificationNative.convertFrom(VResourceKey.identifier(value.sound().orElseThrow().unwrapKey().orElseThrow())),
                    value.hitSound().isEmpty() ? null : IdentificationNative.convertFrom(VResourceKey.identifier(value.hitSound().orElseThrow().unwrapKey().orElseThrow()))
            );
        }

        @Override
        public net.minecraft.world.item.component.KineticWeapon untransform(KineticWeapon value) {
            return new net.minecraft.world.item.component.KineticWeapon(
                    value.contactCooldownTicks,
                    value.delayTicks,
                    value.dismountConditions == null ? Optional.empty() : Optional.of(KineticWeaponCondition.convertFrom(value.dismountConditions)),
                    value.knockbackConditions == null ? Optional.empty() : Optional.of(KineticWeaponCondition.convertFrom(value.knockbackConditions)),
                    value.damageConditions == null ? Optional.empty() : Optional.of(KineticWeaponCondition.convertFrom(value.damageConditions)),
                    value.forwardMovement,
                    value.damageMultiplier,
                    value.sound == null ? Optional.empty() : Optional.of(BuiltInRegistries.SOUND_EVENT.get(IdentificationNative.convert(value.sound)).orElseThrow()),
                    value.hitSound == null ? Optional.empty() : Optional.of(BuiltInRegistries.SOUND_EVENT.get(IdentificationNative.convert(value.hitSound)).orElseThrow())
            );
        }
    }

    public static final _value_type<KineticWeapon> TYPE
                  = new _value_type<>(net.minecraft.world.item.component.KineticWeapon.class, new Controller());
}
