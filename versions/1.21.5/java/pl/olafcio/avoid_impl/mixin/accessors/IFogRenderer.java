package pl.olafcio.avoid_impl.mixin.accessors;

import net.minecraft.client.renderer.FogRenderer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(FogRenderer.class)
public interface IFogRenderer {
    @SuppressWarnings("DataFlowIssue")
    @Accessor("MOB_EFFECT_FOG")
    @NotNull
    static List<FogRenderer.MobEffectFogFunction> avoid$fogEnvironments() {
        return null;
    }
}
