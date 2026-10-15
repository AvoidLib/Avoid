package pl.olafcio.avoid_impl.net.fog;

import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.fog.Fog;
import pl.olafcio.avoid.net.fog.FogState;
import pl.olafcio.avoid_impl.client.AvoidLibClient;
import pl.olafcio.avoid_impl.mixin.accessors.IFogRenderer;
import pl.olafcio.avoid_impl.mixininterface.ICamerable;
import pl.olafcio.avoid_impl.mixininterface.IFogFunction;
import pl.olafcio.avoid_impl.net.entity.EntityNative;
import pl.olafcio.avoid_impl.net.fog.delta.TickTrackerNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid_impl.net.world.WorldNative;

@Native
@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public final class FogsNative {
    @ApiStatus.Internal
    private FogsNative() {}

    private static abstract class FogFunction
                   implements FogRenderer.MobEffectFogFunction, IFogFunction
    {
        @Override
        public Holder<MobEffect> getMobEffect() {
            return null;
        }

        @Override
        public void setupFog(FogRenderer.FogData fogData, LivingEntity livingEntity, MobEffectInstance mobEffectInstance, float f, float g) {}
    }

    public static void register(Fog fog) {
        IFogRenderer.avoid$fogEnvironments().addFirst(new FogFunction() {
            @SuppressWarnings("ConstantValue")
            @Override
            public void avoidlib$setupFog(FogRenderer.FogData fogData, Entity entity, BlockPos blockPos, ClientLevel clientLevel, float f) {
                var state = new FogState();

                if (fogData.mode == FogRenderer.FogMode.FOG_SKY) {
                    state.environmentalStart = Float.MIN_VALUE + 1;
                    state.environmentalEnd = Float.MIN_VALUE + 1;

                    state.skyEnd = fogData.end;
                    state.cloudEnd = fogData.end;
                } else if (fogData.mode == FogRenderer.FogMode.FOG_TERRAIN) {
                    state.environmentalStart = fogData.start;
                    state.environmentalEnd = fogData.end;

                    state.skyEnd = Float.MIN_VALUE + 1;
                    state.cloudEnd = Float.MIN_VALUE + 1;
                } else {
                    return;
                }

                state.renderDistanceStart = fogData.start;
                state.renderDistanceEnd = fogData.end;

                fog.createFog(state, EntityNative.convertFrom(entity), WorldNative.make(clientLevel), f, TickTrackerNative.create(AvoidLibClient.mc.getDeltaTracker()));

                if (fogData.mode == FogRenderer.FogMode.FOG_SKY) {
                    fogData.start = state.renderDistanceStart;
                    fogData.end = Math.min(state.skyEnd, state.renderDistanceEnd);
                } else if (fogData.mode == FogRenderer.FogMode.FOG_TERRAIN) {
                    fogData.start = Math.min(state.renderDistanceEnd, state.renderDistanceStart);
                    fogData.end = Math.min(state.environmentalEnd, state.renderDistanceEnd);
                }
            }

            @Override
            public boolean avoidlib$isApplicable(@Nullable FogType fogType, Entity entity) {
                var fluid = ((ICamerable) entity).avoidlib$inAvoidFluid();
                if (fluid != null) {
                    return fog.shouldApply(fluid.getID(), EntityNative.convertFrom(entity));
                }

                return fog.shouldApply(
                        fogType == null
                                ? null
                                : new Identification("minecraft", fogType.name().toLowerCase()),
                        EntityNative.convertFrom(entity)
                );
            }
        });
    }
}
