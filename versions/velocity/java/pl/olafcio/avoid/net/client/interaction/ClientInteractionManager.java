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

@ApiStatus.Experimental
public final class ClientInteractionManager {
    @ApiStatus.Internal
    private ClientInteractionManager() {}

    public static void interact(Entity entity, HandType type) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#interact() on the server");
    }

    public static void interactAt(Entity entity, IVect3 location, HandType type) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#interactAt() on the server");
    }

    public static void attack(Entity entity) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#attack() on the server");
    }

    public static void destroyBlock(BlockPos blockPos) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#destroyBlock() on the server");
    }

    public static void startDestroyBlock(BlockPos blockPos, Direction direction) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#startDestroyBlock() on the server");
    }

    public static void continueDestroyBlock(BlockPos blockPos, Direction direction) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#continueDestroyBlock() on the server");
    }

    public static void stopDestroyBlock() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#stopDestroyBlock() on the server");
    }

    public static boolean isDestroying() {
        throw new ImproperEnvironment("Cannot ClientInteractionManager#isDestroying() on the server");
    }

    public static void useItem(HandType hand) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#useItem() on the server");
    }

    public static void useItemOn(HandType hand, BlockPos blockPos, Direction direction, boolean isMiss, boolean isInside, boolean isWorldBorder) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot ClientInteractionManager#useItemOn() on the server");
    }
}
