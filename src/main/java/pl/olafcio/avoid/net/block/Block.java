package pl.olafcio.avoid.net.block;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.random.RandomProvider;
import pl.olafcio.avoid.net.block.values.Explosion;
import pl.olafcio.avoid.net.block.values.MapColor;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.block_data.BlockData;

public abstract class Block {
    /**
     * Returns the sort-of <i>color</i> for this block.
     * <br/><br/>
     * This is used among other things for drawing maps.
     * <br/><br/>
     * It is not possible to create your own map colors right now, or it may require hacks<br/>
     * (I think Avoid Block Creation API allows defining those?)
     */
    public abstract MapColor getMapColor();

    public void tick(World world, BlockPos blockPos, RandomProvider randomProvider) {
        this.tick(world, blockPos, (pl.olafcio.avoid.net.block.random.RandomProvider) randomProvider);
    }

    public void randomlyTick(World world, BlockPos blockPos, RandomProvider randomProvider) {
        this.randomlyTick(world, blockPos, (pl.olafcio.avoid.net.block.random.RandomProvider) randomProvider);
    }

    @Deprecated(forRemoval = true, since = "v1.23")
    public void tick(World world, BlockPos blockPos, pl.olafcio.avoid.net.block.random.RandomProvider randomProvider) {}

    @Deprecated(forRemoval = true, since = "v1.23")
    public void randomlyTick(World world, BlockPos blockPos, pl.olafcio.avoid.net.block.random.RandomProvider randomProvider) {}

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

    /**
     * Returns a 0-15 int describing how much light should the block emit.<br/>
     * It works like for light blocks.
     * <br/><br/>
     * Note that Minecraft may not check whether this value is valid.<br/>
     * It may, but additional checking is very recommended.
     * @return The amount of light to emit.<br/>
     *         {@code 0} is none, {@code 15} is max.
     */
    public int emitLight(BlockData blockData) {
        return 0;
    }

    /**
     * Processes the light color.<br/>
     * This method allows you to add colored lightning to your block.
     * <br/><br/>
     * <b>NOTE:</b> This doesn't really work yet!
     */
    @ApiStatus.Experimental
    public int processLight(int value) {
        return value;
    }

    /**
     * Returns whether this block, with the provided parameters, may cause suffocation.
     */
    public boolean isSuffocating(BlockData blockData, World world, BlockPos blockPos) {
        return blockData.blocksMotion() && blockData.isFullSolid(world, blockPos);
    }

    public boolean isViewBlocking(BlockData blockData, World world, BlockPos blockPos) {
        return isSuffocating(blockData, world, blockPos);
    }

    /**
     * Returns whether an entity, of the provided type, can spawn on this block, with the provided parameters.
     */
    public boolean isValidSpawn(BlockData blockData, World world, BlockPos blockPos, EntityType entityType) {
        return blockData.isFaceSturdy(world, blockPos, Direction.UP) && blockData.getLightEmission() < 14;
    }
}
