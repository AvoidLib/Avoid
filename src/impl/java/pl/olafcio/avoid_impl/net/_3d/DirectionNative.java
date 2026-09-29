package pl.olafcio.avoid_impl.net._3d;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net._3d.Direction;

@Native
@ApiStatus.Internal
public final class DirectionNative {
    @ApiStatus.Internal
    private DirectionNative() {}

    public static Direction convert(net.minecraft.core.Direction mc) {
        return switch (mc) {
            case DOWN -> Direction.DOWN;
            case UP -> Direction.UP;
            case NORTH -> Direction.NORTH;
            case SOUTH -> Direction.SOUTH;
            case WEST -> Direction.WEST;
            case EAST -> Direction.EAST;
        };
    }

    public static net.minecraft.core.Direction convertFrom(Direction mc) {
        return switch (mc) {
            case DOWN -> net.minecraft.core.Direction.DOWN;
            case UP -> net.minecraft.core.Direction.UP;
            case NORTH -> net.minecraft.core.Direction.NORTH;
            case SOUTH -> net.minecraft.core.Direction.SOUTH;
            case WEST -> net.minecraft.core.Direction.WEST;
            case EAST -> net.minecraft.core.Direction.EAST;
        };
    }
}
