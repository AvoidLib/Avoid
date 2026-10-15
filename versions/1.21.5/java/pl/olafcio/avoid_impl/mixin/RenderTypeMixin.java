package pl.olafcio.avoid_impl.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid_impl.mixinclass.RenderPipelineMap;

@Mixin(net.minecraft.client.renderer.RenderType.class)
public class RenderTypeMixin {
    @Inject(at = @At("RETURN"), method = "create(Ljava/lang/String;IZZLcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/client/renderer/RenderType$CompositeState;)Lnet/minecraft/client/renderer/RenderType$CompositeRenderType;")
    private static void create(String string, int i, boolean bl, boolean bl2, RenderPipeline renderPipeline, RenderType.CompositeState compositeState, CallbackInfoReturnable<RenderType.CompositeRenderType> cir) {
        RenderPipelineMap.MAP.put(renderPipeline, cir.getReturnValue());
    }
}
