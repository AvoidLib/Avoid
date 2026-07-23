package pl.olafcio.avoid.net.fog;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.mixin.accessors.IFogRenderer;
import pl.olafcio.avoid.mixininterface.ICamerable;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.fog.delta.TickTrackerNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.world.WorldNative;

public final class Fogs {
    @ApiStatus.Internal
    private Fogs() {}

    public static void register(Fog fog) {
        IFogRenderer.avoid$fogEnvironments().addFirst(new FogEnvironment() {
            @Override
            public int getBaseColor(ClientLevel clientLevel, Camera camera, int i, float f) {
                return fog.baseColor(WorldNative.make(clientLevel), EntityNative.convertFrom(camera.entity()), i, f);
            }

            @Override
            public void setupFog(FogData fogData, Camera camera, ClientLevel clientLevel, float f, DeltaTracker deltaTracker) {
                var state = new FogState();

                state.environmentalStart = fogData.environmentalStart;
                state.environmentalEnd = fogData.environmentalEnd;

                state.renderDistanceStart = fogData.renderDistanceStart;
                state.renderDistanceEnd = fogData.renderDistanceEnd;

                state.skyEnd = fogData.skyEnd;
                state.cloudEnd = fogData.cloudEnd;

                fog.createFog(state, EntityNative.convertFrom(camera.entity()), WorldNative.make(clientLevel), f, TickTrackerNative.create(deltaTracker));

                fogData.environmentalStart = state.environmentalStart;
                fogData.environmentalEnd = state.environmentalEnd;

                fogData.renderDistanceStart = state.renderDistanceStart;
                fogData.renderDistanceEnd = state.renderDistanceEnd;

                fogData.skyEnd = state.skyEnd;
                fogData.cloudEnd = state.cloudEnd;
            }

            @Override
            public boolean isApplicable(@Nullable FogType fogType, Entity entity) {
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
