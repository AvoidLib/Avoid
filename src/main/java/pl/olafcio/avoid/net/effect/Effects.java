package pl.olafcio.avoid.net.effect;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.net.effect.internal.IAvoidEffect;
import pl.olafcio.avoid.net.effect.internal.NativeEffect;
import pl.olafcio.avoid.net.effect.properties._category;
import pl.olafcio.avoid.net.effect.properties._color;
import pl.olafcio.avoid.net.effect.values.CategoryNative;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity.values.Damage;
import pl.olafcio.avoid.net.entity.values.DamageNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.world.WorldNative;
import pl.olafcio.avoid.net.world.vect3.Vect3Native;

public final class Effects {
    @ApiStatus.Internal
    private Effects() {}

    @Nullable
    public static Effect get(Identification id) {
        var opt = BuiltInRegistries.MOB_EFFECT.get(IdentificationNative.convert(id));
        if (opt.isEmpty())
            return null;

        var effect = opt.get().value();
        if (effect instanceof IAvoidEffect avoid)
            return avoid.getEffect();

        return new NativeEffect(effect);
    }

    public static void register(Identification id, Effect effect) {
        Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, IdentificationNative.convert(id), new AvoidEffect(effect));
    }

    private static class AvoidEffect extends MobEffect implements IAvoidEffect {
        private final Effect effect;

        public AvoidEffect(Effect effect) {
            super(
                    CategoryNative.convertFrom(effect.getClass().getDeclaredAnnotation(_category.class)
                                                                .value()),
                    effect.getClass().getDeclaredAnnotation(_color.class)
                                     .value()
            );

            this.effect = effect;
        }

        @Override
        public Effect getEffect() {
            return effect;
        }

        @Override
        public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int amplifier) {
            return effect.tryApply(
                    WorldNative.make(serverLevel),
                    EntityNative.convertFrom(livingEntity),
                    amplifier + 1
            );
        }

        @Override
        public boolean shouldApplyEffectTickThisTick(int i, int j) {
            return effect.shouldApply(i, j + 1);
        }

        @Override
        public void onEffectStarted(LivingEntity livingEntity, int i) {
            effect.onAction(EntityNative.convertFrom(livingEntity), i + 1);
        }

        @Override
        public void onMobHurt(ServerLevel serverLevel, LivingEntity livingEntity, int i, DamageSource damageSource, float tickDelta) {
            effect.onMobHurt(
                    WorldNative.make(serverLevel),
                    EntityNative.convertFromTry(livingEntity),
                    i+1,
                    DamageNative.convert(damageSource, AvoidInternal.getServer().registryAccess()),
                    tickDelta
            );
        }
    }
}
