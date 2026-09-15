package pl.olafcio.avoid.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
import net.minecraft.client.renderer.state.LevelRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mixininterface.IBlockOutlineRenderState;
import pl.olafcio.avoid.net.world.WorldNative;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Shadow
    protected abstract void renderHitOutline(PoseStack poseStack, VertexConsumer vertexConsumer, double d, double e, double f, BlockOutlineRenderState blockOutlineRenderState, int i, float g);

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(at = @At("TAIL"), method = "renderBlockOutline")
    public void renderBlockOutline(MultiBufferSource.BufferSource bufferSource, PoseStack poseStack, boolean translucent, LevelRenderState levelRenderState, CallbackInfo ci) {
        for (var blockOutlineRenderState : WorldNative.highlights) {
            if (blockOutlineRenderState.isTranslucent() == translucent) {
                var custom = (IBlockOutlineRenderState) (Object) blockOutlineRenderState;
                var camera = levelRenderState.cameraRenderState.pos;

                var secondaryColor = blockOutlineRenderState.highContrast()

                                        ? custom.secondaryColor_highcontrast()
                                        : custom.secondaryColor();

                if (secondaryColor != null) {
                    var vertexConsumer = bufferSource.getBuffer(RenderTypes.secondaryBlockOutline());
                    this.renderHitOutline(
                            poseStack, vertexConsumer,
                            camera.x, camera.y, camera.z,
                            blockOutlineRenderState,
                            secondaryColor,//at:linewidth
                            custom.lineWidth_secondary()
                            //at:lwend
                    );
                }

                var color = blockOutlineRenderState.highContrast() ? custom.color_highcontrast() : custom.color();
                if (color != null) {
                    var vertexConsumer = bufferSource.getBuffer(RenderTypes.lines());

                    this.renderHitOutline(
                            poseStack, vertexConsumer,
                            camera.x, camera.y, camera.z,
                            blockOutlineRenderState,
                            color,//at:linewidth
                            custom.lineWidth() == null
                                    ? this.minecraft.getWindow().getAppropriateLineWidth()
                                    : custom.lineWidth()
                            //at:lwend
                    );
                }

                bufferSource.endLastBatch();
            }
        }
    }
}
