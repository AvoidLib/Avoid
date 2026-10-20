package pl.olafcio.avoid_impl.mixin;

import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Shadow
    @Mutable
    @Final
    private static List<FogEnvironment> FOG_ENVIRONMENTS;

    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void clinit(CallbackInfo ci) {
        FOG_ENVIRONMENTS = new ArrayList<>(FOG_ENVIRONMENTS);
    }
}
