package pl.olafcio.avoid.net.fluid;

import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.block.properties._liquid;
import pl.olafcio.avoid.net.block.random.RandomProvider;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.fluid.inside_block.InsideBlock;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.item.Item;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.block_data.BlockData;

/**
 * A flowable fluid.
 * <br/><br/>
 * An instance of this is made during registry init (only <u>once</u> for the whole game).<br/>
 * To access the state, use the {@link FluidState fluidState} parameter provided to your method.<br/>
 * If the method you're working on doesn't have a state parameter, it's <i><b>static</b> (in this system)</i>.
 */
@NullMarked
public abstract class Fluid {
    public abstract Item getBucket();

    protected static final boolean CALL_SUPER = true;
    protected static final boolean OK = false;

    public boolean animateTick(World world, BlockPos blockPos, FluidState fluidState, RandomProvider randomProvider) {
        return CALL_SUPER;
    }

    public boolean tick(World world, BlockPos blockPos, FluidState fluidState, BlockData blockData) {
        return CALL_SUPER;
    }

    public boolean randomTick(World world, BlockPos blockPos, FluidState fluidState, RandomProvider randomProvider) {
        return CALL_SUPER;
    }

    public boolean isRandomlyTicking() {
        return false;
    }

    /**
     * Called when a player is inside the fluid.<br/>
     * The default behaviour is no-op - nothing happens.
     */
    public void onEntityEncounter(World world, BlockPos blockPos, Entity entity, InsideBlock insideBlock) {
    }

    /**
     * Called before the fluid destroys a block.<br/>
     * The default behaviour is like water - the target block drops.
     */
    public void onBlockEncounter(World world, BlockPos blockPos, BlockData blockData) {
        world.drop(blockPos);
    }

    /**
     * I guess fluids can be exploded (potential for oil, maybe)?<br/>
     * The default behaviour is like lava and water - 100.
     */
    protected float getExplosionResistance() {
        return 100.0F;
    }

    /**
     * Returns the ticking delay (kind of animation TPS) of the fluid.<br/>
     * The default value is like water.
     */
    public int getTickDelay(World world) {
        return 5;
    }

    /**
     * Returns the speed of the fluid.<br/>
     * The default value is like water.<br/>
     * <b>NOTE:</b> The larger the number, the slower the fluid!
     */
    public int getSpeed(World world) {
        return 4;
    }

    /**
     * The goal amount of blocks to spread to from the source (source is the origin fluid/placed position).<br/>
     * The default value is like water.
     */
    public int getSpread(FluidState state) {
        return 8;
    }

    /**
     * I have no fucking clue what this does<br/>
     * The default value is like water.<br/>
     */
    public int getDropOff(World world) {
        return 1;
    }

    /**
     * Returns whether the fluid flowing form can be transformed into a source.<br/>
     * In other words, whether the fluid is infinite.
     */
    public boolean isSourceable(World world) {
        return true;
    }

    public boolean isReplaceable(FluidState state, World world, BlockPos blockPos, Fluid fluid, Direction direction) {
        return false;
    }

    /**
     * Creates an instance of the fluid block.<br/>
     * <b>NOTE:</b> The returned block type must use a fluided {@link _liquid @_liquid} annotation!
     */
    public abstract BlockData createBlock(FluidState state);

    Identification id;
    net.minecraft.world.level.material.Fluid fluid;

    public final Identification getID() {
        return id;
    }
}
