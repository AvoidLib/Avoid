package pl.olafcio.avoid.mixin;

import com.google.common.collect.Maps;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.block.FluidStateModelSet;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.MaterialBaker;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.fluid.FluidStateNative;
import pl.olafcio.avoid.net.fluid.FluidsNative;
import pl.olafcio.avoid.net.fluid.properties._layer;
import pl.olafcio.avoid.net.fluid.properties._model;
import pl.olafcio.avoid.net.fluid.properties.layer.ChunkLayer;
import pl.olafcio.avoid.net.world.block_data.BlockDataNative;

import java.util.Map;

@Mixin(FluidStateModelSet.class)
public class FluidStateModelSetMixin {
    @WrapMethod(method = "bake")
    private static Map<Fluid, FluidModel> bake(MaterialBaker materials, Operation<Map<Fluid, FluidModel>> original) {
        var map = original.call(materials);
        var hashmap = Maps.newHashMap(map);

        for (var entry : FluidsNative.instances.entrySet()) {
            var fluid = entry.getKey();
            var klass = fluid.getClass();

            Identifier still;
            Identifier flowing;

            if (klass.isAnnotationPresent(_model.class)) {
                var models = klass.getDeclaredAnnotation(_model.class);

                var path1 = models.still().path();
                var path2 = models.flowing().path();

                if (!path1.startsWith("block/"))
                    path1 = "block/" + path1;

                if (!path2.startsWith("block/"))
                    path2 = "block/" + path2;

                still = Identifier.fromNamespaceAndPath(models.still().namespace(), path1);
                flowing = Identifier.fromNamespaceAndPath(models.flowing().namespace(), path2);
            } else {
                still = Identifier.withDefaultNamespace("block/water_still");
                flowing = Identifier.withDefaultNamespace("block/water_flow");
            }

            var translucent = klass.isAnnotationPresent(_layer.class) &&
                    klass.getDeclaredAnnotation(_layer.class).value() == ChunkLayer.TRANSLUCENT;

            var unbaked = new FluidModel.Unbaked(
                    new Material(still, translucent),
                    new Material(flowing, translucent),
                    new Material(Identifier.withDefaultNamespace("block/water_overlay")),
                    new BlockTintSource() {
                        @Override
                        public int color(final BlockState state) {
                            return -1;
                        }

                        @Override
                        public int colorInWorld(final BlockState state, final BlockAndTintGetter level, final BlockPos pos) {
                            return fluid.getColor(
                                    BlockPosNative.convert(pos),
                                    BlockDataNative.convertFrom(state),
                                    FluidStateNative.create(level.getFluidState(pos)),
                                    BiomeColors.getAverageWaterColor(level, pos)
                            );
                        }
                    }
            );

            var baken = unbaked.bake(materials, klass::getName);

            hashmap.put(entry.getValue().getFlowing(), baken);
            hashmap.put(entry.getValue().getSource(), baken);
        }

        return hashmap;
    }
}
