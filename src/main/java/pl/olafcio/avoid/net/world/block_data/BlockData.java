package pl.olafcio.avoid.net.world.block_data;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.block.Block;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.block.random.RandomProvider;
import pl.olafcio.avoid.net.block.random.RandomProviderNative;
import pl.olafcio.avoid.net.block.values.MapColor;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.WorldNative;

@NeverRemoval
public final class BlockData extends Block {
    final BlockState state;

    BlockData(BlockState state) {
        this.state = state;
    }

    @Override
    @ApiStatus.Experimental
    public MapColor getMapColor() {
        return null;
    }

    @Override
    @NeverRemoval
    public void tick(World world, BlockPos blockPos, RandomProvider randomProvider) {
        state.tick(
                (ServerLevel) WorldNative.convert(world),
                BlockPosNative.convertFrom(blockPos),
                RandomProviderNative.convert(randomProvider)
        );
    }

    @Override
    @NeverRemoval
    public void randomlyTick(World world, BlockPos blockPos, RandomProvider randomProvider) {
        state.randomTick(
                (ServerLevel) WorldNative.convert(world),
                BlockPosNative.convertFrom(blockPos),
                RandomProviderNative.convert(randomProvider)
        );
    }
}
