package pl.olafcio.avoid.net.block;

import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.block.random.RandomProvider;
import pl.olafcio.avoid.net.block.values.Explosion;
import pl.olafcio.avoid.net.block.values.MapColor;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.block_data.BlockData;

public abstract class Block {
    public abstract MapColor getMapColor();

    public void tick(World world, BlockPos blockPos, RandomProvider randomProvider) {}
    public void randomlyTick(World world, BlockPos blockPos, RandomProvider randomProvider) {}

    public boolean dropFromExplosion(Explosion explosion) {
        return true;
    }

    /**
     * @return If {@code false}, the default destroying method is called.<br/>
     *         If {@code true}, it isn't (you should do your own special handling).
     */
    public boolean destroy(World world, BlockPos blockPos, BlockData blockData) {
        return false;
    }
}
