package pl.olafcio.avoid.net.world;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import pl.olafcio.avoid.net.block.Block;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.block.random.RandomProvider;
import pl.olafcio.avoid.net.block.random.RandomProviderNative;
import pl.olafcio.avoid.net.block.values.MapColor;

public final class BlockData extends Block {
    private final BlockState state;

    BlockData(BlockState state) {
        this.state = state;
    }

    @Override
    public MapColor getMapColor() {
        return null;
    }

    @Override
    public void tick(World world, BlockPos blockPos, RandomProvider randomProvider) {
        state.tick(
                (ServerLevel) WorldNative.convert(world),
                BlockPosNative.convertFrom(blockPos),
                RandomProviderNative.convert(randomProvider)
        );
    }

    @Override
    public void randomlyTick(World world, BlockPos blockPos, RandomProvider randomProvider) {
        state.randomTick(
                (ServerLevel) WorldNative.convert(world),
                BlockPosNative.convertFrom(blockPos),
                RandomProviderNative.convert(randomProvider)
        );
    }
}
