package pl.olafcio.avoid.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.mixininterface.IBlockOutlineRenderState;
import pl.olafcio.avoid.net.block.BlockNative.IAvoidBlock;
import pl.olafcio.avoid.net.world.WorldNative;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Shadow
    protected abstract void renderHitOutline(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, double d, double e, double f, BlockPos blockPos, BlockState blockState, int i);

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Nullable
    private ClientLevel level;

    @Inject(at = @At("TAIL"), method = "renderBlockOutline")
    public void renderBlockOutline(Camera camobj, MultiBufferSource.BufferSource bufferSource, PoseStack poseStack, boolean translucent, CallbackInfo ci) {
        var campos = camobj.getPosition();
        var highContrast = this.minecraft.options.highContrastBlockOutline().get();

        for (var blockOutlineRenderState : WorldNative.highlights) {
            if (blockOutlineRenderState.isTranslucent() == translucent) {
                var custom = (IBlockOutlineRenderState) (Object) blockOutlineRenderState;

                var secondaryColor = highContrast

                                        ? custom.secondaryColor_highcontrast()
                                        : custom.secondaryColor();

                if (secondaryColor != null) {
                    var vertexConsumer = bufferSource.getBuffer(RenderType.secondaryBlockOutline());
                    this.renderHitOutline(
                            poseStack, vertexConsumer,
                            camobj.getEntity(),
                            campos.x, campos.y, campos.z,
                            blockOutlineRenderState.pos(),
                            this.level.getBlockState(blockOutlineRenderState.pos()),
                            secondaryColor/*
                            custom.lineWidth_secondary()
                            */
                    );
                }

                var color = highContrast ? custom.color_highcontrast() : custom.color();
                if (color != null) {
                    var vertexConsumer = bufferSource.getBuffer(RenderType.lines());

                    this.renderHitOutline(
                            poseStack, vertexConsumer,
                            camobj.getEntity(),
                            campos.x, campos.y, campos.z,
                            blockOutlineRenderState.pos(),
                            this.level.getBlockState(blockOutlineRenderState.pos()),
                            color/*
                            custom.lineWidth() == null
                                    ? this.minecraft.getWindow().getAppropriateLineWidth()
                                    : custom.lineWidth()
                            */
                    );
                }

                bufferSource.endLastBatch();
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "getLightColor(Lnet/minecraft/client/renderer/LevelRenderer$BrightnessGetter;Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)I", cancellable = true)
    private static void getLightColor(LevelRenderer.BrightnessGetter brightnessGetter, BlockAndTintGetter blockAndTintGetter, BlockState blockState, BlockPos blockPos, CallbackInfoReturnable<Integer> cir) {
        if (blockState.getBlock() instanceof IAvoidBlock avoid)
            cir.setReturnValue(avoid.avoid$block().processLight(cir.getReturnValueI()));
    }
}
