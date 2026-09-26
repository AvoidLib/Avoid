package pl.olafcio.avoid.net.world.block_data;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.Block;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.random.RandomProvider;
import pl.olafcio.avoid.net.block.values.MapColor;
import pl.olafcio.avoid.net.world.World;

@NeverRemoval
@ApiStatus.NonExtendable
public abstract class BlockData extends Block {
    @Override
    @ApiStatus.Experimental
    public MapColor getMapColor() {
        return null;
    }

    @Override
    @NeverRemoval
    public abstract void tick(World world, BlockPos blockPos, RandomProvider randomProvider);

    @Override
    @NeverRemoval
    public abstract void randomlyTick(World world, BlockPos blockPos, RandomProvider randomProvider);

    @Override
    @Deprecated(forRemoval = true, since = "v1.23")
    public abstract void tick(World world, BlockPos blockPos, pl.olafcio.avoid.net.block.random.RandomProvider randomProvider);

    @Override
    @Deprecated(forRemoval = true, since = "v1.23")
    public abstract void randomlyTick(World world, BlockPos blockPos, pl.olafcio.avoid.net.block.random.RandomProvider randomProvider);

    /**
     * Returns whether a block with this data causes the player to suffocate, and thus block his vision (typically rendering
     *         the causing block texture).
     */
    @ApiStatus.Experimental
    public abstract boolean blocksMotion();

    @ApiStatus.Experimental
    public abstract int getLightEmission();

    /**
     * Returns whether the block collision expands to a whole block hitbox.
     */
    public abstract boolean isFullSolid(World world, BlockPos blockPos);

    public abstract boolean isFaceSturdy(World world, BlockPos blockPos, Direction direction);
}
