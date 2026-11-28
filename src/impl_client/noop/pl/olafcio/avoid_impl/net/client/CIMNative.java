package pl.olafcio.avoid_impl.net.client;

import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.player_server.values.HandType;
import pl.olafcio.avoid.net.world.vect3.IVect3;

public enum CIMNative {
    ;

    public static boolean nah() {
        throw new UnsupportedOperationException();
    }

    public static void interact(Entity entity, HandType type) {
        throw new UnsupportedOperationException();
    }

    public static void interactAt(Entity entity, IVect3 location, HandType type) {
        throw new UnsupportedOperationException();
    }

    public static void attack(Entity entity) {
        throw new UnsupportedOperationException();
    }

    public static void destroyBlock(BlockPos blockPos) {
        throw new UnsupportedOperationException();
    }

    public static void startDestroyBlock(BlockPos blockPos, Direction direction) {
        throw new UnsupportedOperationException();
    }

    public static void continueDestroyBlock(BlockPos blockPos, Direction direction) {
        throw new UnsupportedOperationException();
    }

    public static void stopDestroyBlock() {
        throw new UnsupportedOperationException();
    }

    public static boolean isDestroying() {
        throw new UnsupportedOperationException();
    }

    public static void useItem(HandType hand) {
        throw new UnsupportedOperationException();
    }

    public static void useItemOn(HandType hand, BlockPos blockPos, Direction direction, boolean isMiss, boolean isInside, boolean isWorldBorder) {
        throw new UnsupportedOperationException();
    }
}
