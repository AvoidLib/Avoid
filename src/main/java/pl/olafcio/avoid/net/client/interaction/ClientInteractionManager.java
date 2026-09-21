package pl.olafcio.avoid.net.client.interaction;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.player_server.values.HandType;
import pl.olafcio.avoid.net.world.vect3.IVect3;
import pl.olafcio.avoid_impl.net.client.CIMNative;

import static pl.olafcio.avoid_impl.net.client.CIMNative.nah;

@ApiStatus.Experimental
public final class ClientInteractionManager {
    @ApiStatus.Internal
    private ClientInteractionManager() {}

    public static void interact(Entity entity, HandType type) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#interact() on the server");

        if (nah()) return;
        CIMNative.interact(entity, type);
    }

    public static void interactAt(Entity entity, IVect3 location, HandType type) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#interactAt() on the server");

        if (nah()) return;
        CIMNative.interactAt(entity, location, type);
    }

    public static void attack(Entity entity) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#attack() on the server");

        if (nah()) return;
        CIMNative.attack(entity);
    }

    public static void destroyBlock(BlockPos blockPos) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#destroyBlock() on the server");

        if (nah()) return;
        CIMNative.destroyBlock(blockPos);
    }

    public static void startDestroyBlock(BlockPos blockPos, Direction direction) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#startDestroyBlock() on the server");

        if (nah()) return;
        CIMNative.startDestroyBlock(blockPos, direction);
    }

    public static void continueDestroyBlock(BlockPos blockPos, Direction direction) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#continueDestroyBlock() on the server");

        if (nah()) return;
        CIMNative.continueDestroyBlock(blockPos, direction);
    }

    public static void stopDestroyBlock() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#stopDestroyBlock() on the server");

        if (nah()) return;
        CIMNative.stopDestroyBlock();
    }

    public static boolean isDestroying() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#isDestroying() on the server");

        if (nah()) return false;
        return CIMNative.isDestroying();
    }

    public static void useItem(HandType hand) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#useItem() on the server");

        if (nah()) return;
        CIMNative.useItem(hand);
    }

    public static void useItemOn(HandType hand, BlockPos blockPos, Direction direction, boolean isMiss, boolean isInside, boolean isWorldBorder) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#useItemOn() on the server");

        if (nah()) return;
        CIMNative.useItemOn(hand, blockPos, direction, isMiss, isInside, isWorldBorder);
    }
}
