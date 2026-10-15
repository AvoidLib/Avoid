package pl.olafcio.avoid_impl.mixininterface;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import org.jspecify.annotations.Nullable;

public interface IFogFunction {
    void avoidlib$setupFog(FogRenderer.FogData fogData, Entity entity, BlockPos blockPos, ClientLevel clientLevel, float f);
    boolean avoidlib$isApplicable(@Nullable FogType fogType, Entity entity);
}
