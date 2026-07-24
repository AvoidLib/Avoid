package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.fluid.AvoidFluid;
import pl.olafcio.avoid.net.fluid.FluidNative;
import pl.olafcio.avoid.net.fluid.FluidStateNative;
import pl.olafcio.avoid.net.world.block_data.BlockDataNative;

@Mixin(LiquidBlockRenderer.class)
public class LiquidBlockRendererMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BiomeColors;getAverageWaterColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;)I"), method = "tesselate")
    public int tesselate__getColor(BlockAndTintGetter blockAndTintGetter1, BlockPos blockPos1, Operation<Integer> original, BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState) {
        var value = original.call(blockAndTintGetter1, blockPos1);
        if (fluidState.getType() instanceof AvoidFluid avoid)
            return FluidNative.get(avoid).getColor(BlockPosNative.convert(blockPos), BlockDataNative.convertFrom(blockState), FluidStateNative.create(fluidState), value);

        return value;
    }
}
