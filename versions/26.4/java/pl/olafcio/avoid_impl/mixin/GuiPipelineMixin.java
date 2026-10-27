package pl.olafcio.avoid_impl.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiPipeline;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid_impl.mixinclass.GuiPipelineMap;

import java.util.function.Supplier;

@Mixin(GuiPipeline.class)
public class GuiPipelineMixin {
    @Inject(at = @At("TAIL"), method = "<init>")
    public void init(Supplier<RenderPipeline> pipelineSupplier, CallbackInfo ci) {
        GuiPipelineMap.MAP.put(pipelineSupplier.get(), (GuiPipeline) (Object) this);
    }
}
