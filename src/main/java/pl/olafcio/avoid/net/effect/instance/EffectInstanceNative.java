package pl.olafcio.avoid.net.effect.instance;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.internal.VResourceKey;
import pl.olafcio.avoid.net.id.IdentificationNative;

@Native
@ApiStatus.Internal
public final class EffectInstanceNative {
    @ApiStatus.Internal
    private EffectInstanceNative() {}

    public static EffectInstance convert(MobEffectInstance effect) {
        return new EffectInstance(
                IdentificationNative.convertFrom(VResourceKey.identifier(effect.getEffect().unwrapKey().orElseThrow())),
                effect.getDuration(),
                effect.getAmplifier() + 1
        );
    }

    public static MobEffectInstance convertFrom(EffectInstance effectInstance) {
        return new MobEffectInstance(
                BuiltInRegistries.MOB_EFFECT.get(IdentificationNative.convert(effectInstance.getID())).orElseThrow(),
                effectInstance.getDuration(),
                effectInstance.getLevel() - 1
        );
    }
}
