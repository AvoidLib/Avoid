package pl.olafcio.avoid.net.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.block.properties._liquid;
import pl.olafcio.avoid.net.block.random.RandomProviderNative;
import pl.olafcio.avoid.net.fluid.Fluid;
import pl.olafcio.avoid.net.fluid.FluidsNative;
import pl.olafcio.avoid.net.fluid.Fluids;
import pl.olafcio.avoid.net.world.WorldNative;

import java.lang.reflect.InvocationTargetException;

@Native
@ApiStatus.Internal
public final class BlockNative {
    @ApiStatus.Internal
    private BlockNative() {}

    public static Block make(pl.olafcio.avoid.net.block.Block avoidBlock, BlockBehaviour.Properties properties) {
        var abc = avoidBlock.getClass();
        if (abc.isAnnotationPresent(_liquid.class)) {
            var liquid = abc.getDeclaredAnnotation(_liquid.class);
            if (liquid.fluid() != Fluid.class)
                return makeLiquid(liquid.fluid(), avoidBlock, properties);
        }

        return new Block(properties) {
            @Override
            protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
                super.tick(blockState, serverLevel, blockPos, randomSource);

                avoidBlock.tick(
                        WorldNative.make(serverLevel),
                        BlockPosNative.convert(blockPos),
                        RandomProviderNative.create(randomSource)
                );
            }

            @Override
            protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
                super.randomTick(blockState, serverLevel, blockPos, randomSource);

                avoidBlock.randomlyTick(
                        WorldNative.make(serverLevel),
                        BlockPosNative.convert(blockPos),
                        RandomProviderNative.create(randomSource)
                );
            }
        };
    }

    private static LiquidBlock makeLiquid(Class<? extends Fluid> fluidClass, pl.olafcio.avoid.net.block.Block avoidBlock, BlockBehaviour.Properties properties) {
        return new LiquidBlock(FluidsNative.convertFrom(fluidClass), properties) {
            @Override
            protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
                super.tick(blockState, serverLevel, blockPos, randomSource);

                avoidBlock.tick(
                        WorldNative.make(serverLevel),
                        BlockPosNative.convert(blockPos),
                        RandomProviderNative.create(randomSource)
                );
            }

            @Override
            protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
                super.randomTick(blockState, serverLevel, blockPos, randomSource);

                avoidBlock.randomlyTick(
                        WorldNative.make(serverLevel),
                        BlockPosNative.convert(blockPos),
                        RandomProviderNative.create(randomSource)
                );
            }
        };
    }
}
