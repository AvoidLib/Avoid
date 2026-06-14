package pl.olafcio.avoid.net.block;

import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.block.random.RandomProvider;
import pl.olafcio.avoid.net.block.values.MapColor;
import pl.olafcio.avoid.net.world.World;

public abstract class Block {
    private void use() {}
    public abstract MapColor getMapColor();

    public void tick(World world, BlockPos blockPos, RandomProvider randomProvider) {}
    public void randomlyTick(World world, BlockPos blockPos, RandomProvider randomProvider) {}
}
