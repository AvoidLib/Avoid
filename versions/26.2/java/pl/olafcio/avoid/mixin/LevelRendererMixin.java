package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.BlockOutlineRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
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
    protected abstract void submitHitOutline(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, RenderType renderType, BlockOutlineRenderState state, int color, float width, boolean afterTerrain);

    @Shadow
    @Final
    private GameRenderer gameRenderer;

    @Inject(at = @At("TAIL"), method = "submitBlockOutline")
    public void renderBlockOutline(
            PoseStack poseStack, SubmitNodeCollector submitNodeCollector, LevelRenderState levelRenderState,
            CallbackInfo ci,
            @Local(name = "state") BlockOutlineRenderState state, @Local(name = "cameraPos") Vec3 cameraPos, @Local(name = "pos") BlockPos pos
    ) {
        poseStack.pushPose();
        poseStack.translate((double)pos.getX() - cameraPos.x, (double)pos.getY() - cameraPos.y, (double)pos.getZ() - cameraPos.z);

        for (var blockOutlineRenderState : WorldNative.highlights) {
            if (blockOutlineRenderState.isTranslucent() == state.isTranslucent()) {
                var custom = (IBlockOutlineRenderState) (Object) blockOutlineRenderState;

                var secondaryColor = blockOutlineRenderState.highContrast()

                                        ? custom.secondaryColor_highcontrast()
                                        : custom.secondaryColor();

                if (secondaryColor != null) {
                    this.submitHitOutline(
                            poseStack, submitNodeCollector,
                            RenderTypes.secondaryBlockOutline(),
                            state,
                            secondaryColor,//at:linewidth
                            custom.lineWidth_secondary(),
                            state.isTranslucent()
                    );
                }

                var color = blockOutlineRenderState.highContrast() ? custom.color_highcontrast() : custom.color();
                if (color != null) {
                    this.submitHitOutline(
                            poseStack, submitNodeCollector,
                            RenderTypes.lines(),
                            blockOutlineRenderState,
                            color,//at:linewidth
                            custom.lineWidth() == null
                                    ? this.gameRenderer.gameRenderState().windowRenderState.appropriateLineWidth
                                    : custom.lineWidth(),
                            state.isTranslucent()
                            //at:lwend
                    );
                }
            }
        }

        poseStack.popPose();
    }
}
