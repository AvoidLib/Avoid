package pl.olafcio.avoid_impl.net.world.block_data;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.random.RandomProvider;
import pl.olafcio.avoid.net.random.RandomProviderNative;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid_impl.net.world.WorldNative;
import pl.olafcio.avoid_impl.net.block.pos.BlockPosNative;

public final class BlockData extends pl.olafcio.avoid.net.world.block_data.BlockData {
    final BlockState state;

    BlockData(BlockState state) {
        this.state = state;
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

    @Override
    @Deprecated(forRemoval = true, since = "v1.23")
    public void tick(World world, BlockPos blockPos, pl.olafcio.avoid.net.block.random.RandomProvider randomProvider) {
        state.tick(
                (ServerLevel) WorldNative.convert(world),
                BlockPosNative.convertFrom(blockPos),
                RandomProviderNative.convert(randomProvider)
        );
    }

    @Override
    @Deprecated(forRemoval = true, since = "v1.23")
    public void randomlyTick(World world, BlockPos blockPos, pl.olafcio.avoid.net.block.random.RandomProvider randomProvider) {
        state.randomTick(
                (ServerLevel) WorldNative.convert(world),
                BlockPosNative.convertFrom(blockPos),
                RandomProviderNative.convert(randomProvider)
        );
    }

    @ApiStatus.Experimental
    @Override
    public boolean blocksMotion() {
        return state.blocksMotion();
    }

    @ApiStatus.Experimental
    @Override
    public int getLightEmission() {
        return state.getLightEmission();
    }

    @Override
    public boolean isFullSolid(World world, BlockPos blockPos) {
        return state.isCollisionShapeFullBlock(WorldNative.convert(world), BlockPosNative.convertFrom(blockPos));
    }

    @Override
    public boolean isFaceSturdy(World world, BlockPos blockPos, Direction direction) {
        return state.isFaceSturdy(WorldNative.convert(world), BlockPosNative.convertFrom(blockPos), net.minecraft.core.Direction.valueOf(direction.name()));
    }
}
