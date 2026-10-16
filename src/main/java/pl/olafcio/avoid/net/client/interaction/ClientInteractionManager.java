package pl.olafcio.avoid.net.client.interaction;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.platform.AvoidWrappedLoader;
import pl.olafcio.avoid.errors.ImproperEnvironment;
import pl.olafcio.avoid.platform.RunningEnv;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.player_server.values.HandType;
import pl.olafcio.avoid.net.world.vect3.IVect3;
import pl.olafcio.avoid_impl.net.client.CIMNative;

import static pl.olafcio.avoid_impl.net.client.CIMNative.nah;

/**
 * A namespace for managing interactions on the client (from the local player) - touching blocks, items and players.
 */
@ApiStatus.Experimental
public final class ClientInteractionManager {
    @ApiStatus.Internal
    private ClientInteractionManager() {}

    /**
     * Interacts with the specified entity using the specified handtype.
     */
    public static void interact(Entity entity, HandType type) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#interact() on the server");

        if (nah()) return;
        CIMNative.interact(entity, type);
    }

    /**
     * Interacts with the specified entity using the specified handtype assuming the entity is located at the specified location.
     */
    public static void interactAt(Entity entity, IVect3 location, HandType type) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#interactAt() on the server");

        if (nah()) return;
        CIMNative.interactAt(entity, location, type);
    }

    /**
     * Attacks the specified entity.
     */
    public static void attack(Entity entity) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#attack() on the server");

        if (nah()) return;
        CIMNative.attack(entity);
    }

    /**
     * Tries to destroy the specified block.
     */
    public static void destroyBlock(BlockPos blockPos) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#destroyBlock() on the server");

        if (nah()) return;
        CIMNative.destroyBlock(blockPos);
    }

    /**
     * Tries to start destroying the specified block.
     */
    public static void startDestroyBlock(BlockPos blockPos, Direction direction) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#startDestroyBlock() on the server");

        if (nah()) return;
        CIMNative.startDestroyBlock(blockPos, direction);
    }

    /**
     * Tries to continue destroying the specified block.
     */
    public static void continueDestroyBlock(BlockPos blockPos, Direction direction) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#continueDestroyBlock() on the server");

        if (nah()) return;
        CIMNative.continueDestroyBlock(blockPos, direction);
    }

    /**
     * Tries to stop destroying the specified block.
     */
    public static void stopDestroyBlock() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#stopDestroyBlock() on the server");

        if (nah()) return;
        CIMNative.stopDestroyBlock();
    }

    /**
     * Returns whether the player is currently destroying a block.
     */
    public static boolean isDestroying() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#isDestroying() on the server");

        if (nah()) return false;
        return CIMNative.isDestroying();
    }

    /**
     * Tries to use the item held in the specified hand type.
     */
    public static void useItem(HandType hand) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#useItem() on the server");

        if (nah()) return;
        CIMNative.useItem(hand);
    }

    /**
     * Tries to use the item held in the specified hand type, clicking at the specified blockpos at the specified direction,
     * specifying whether will it be a miss, a hit, or targeted at the world border.
     */
    public static void useItemOn(HandType hand, BlockPos blockPos, Direction direction, boolean isMiss, boolean isInside, boolean isWorldBorder) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#useItemOn() on the server");

        if (nah()) return;
        CIMNative.useItemOn(hand, blockPos, direction, isMiss, isInside, isWorldBorder);
    }
}
