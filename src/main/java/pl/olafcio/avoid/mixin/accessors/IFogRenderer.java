package pl.olafcio.avoid.mixin.accessors;

import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(FogRenderer.class)
public interface IFogRenderer {
    @SuppressWarnings("DataFlowIssue")
    @Accessor("FOG_ENVIRONMENTS")
    @NotNull
    static List<FogEnvironment> avoid$fogEnvironments() {
        return null;
    }
}
