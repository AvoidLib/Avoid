package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mixinclass.FluidModels;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.fluid.*;
import pl.olafcio.avoid.net.fluid.properties._model;
import pl.olafcio.avoid.net.world.block_data.BlockDataNative;

import java.util.HashMap;

@Mixin(LiquidBlockRenderer.class)
public class LiquidBlockRendererMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BiomeColors;getAverageWaterColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;)I"), method = "tesselate")
    public int tesselate__getColor(BlockAndTintGetter blockAndTintGetter1, BlockPos blockPos1, Operation<Integer> original, BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState) {
        var value = original.call(blockAndTintGetter1, blockPos1);
        if (fluidState.getType() instanceof AvoidFluid avoid)
            return FluidNative.get(avoid).getColor(BlockPosNative.convert(blockPos), BlockDataNative.convertFrom(blockState), FluidStateNative.create(fluidState), value);

        return value;
    }

    @WrapOperation(at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;waterIcons:[Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", opcode = Opcodes.GETFIELD), method = "tesselate")
    public TextureAtlasSprite[] tesselate__models(LiquidBlockRenderer instance, Operation<TextureAtlasSprite[]> original, BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState) {
        if (fluidState.getType() instanceof AvoidFluid avoid) {
            var klass = FluidNative.get(avoid).getClass();
            if (models.containsKey(klass))
                return new TextureAtlasSprite[]{
                        models.get(klass).still(),
                        models.get(klass).flowing()
                };
        }

        return original.call(instance);
    }

    @Unique
    private final HashMap<Class<? extends Fluid>, FluidModels> models
            = new HashMap<>();

    @Inject(at = @At("TAIL"), method = "setupSprites")
    public void init(CallbackInfo ci) {
        for (var fluid : FluidsNative.classes.keySet()) {
            if (fluid.isAnnotationPresent(_model.class)) {
                var models = fluid.getDeclaredAnnotation(_model.class);

                var modelStill = new Material(TextureAtlas.LOCATION_BLOCKS, Identifier.fromNamespaceAndPath(models.still().namespace(), models.still().path()).withPrefix("block/")).sprite();
                var modelFlowing = new Material(TextureAtlas.LOCATION_BLOCKS, Identifier.fromNamespaceAndPath(models.flowing().namespace(), models.flowing().path()).withPrefix("block/")).sprite();

                this.models.put(fluid, new FluidModels(modelStill, modelFlowing));
            }
        }
    }
}
