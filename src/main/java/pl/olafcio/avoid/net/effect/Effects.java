package pl.olafcio.avoid.net.effect;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.effect.properties._category;
import pl.olafcio.avoid.net.effect.properties._color;
import pl.olafcio.avoid.net.effect.values.CategoryNative;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.world.WorldNative;

public final class Effects {
    @ApiStatus.Internal
    private Effects() {}

    public static void register(Identification id, Effect effect) {
        Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, IdentificationNative.convert(id), new MobEffect(
                CategoryNative.convertFrom(effect.getClass().getDeclaredAnnotation(_category.class)
                                                            .value()),
                effect.getClass().getDeclaredAnnotation(_color.class)
                                 .value()
        ) {
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
        });
    }
}
