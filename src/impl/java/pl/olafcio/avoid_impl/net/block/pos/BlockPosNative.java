package pl.olafcio.avoid_impl.net.block.pos;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.block.pos.BlockPos;

@Native
@ApiStatus.Internal
public final class BlockPosNative {
    @ApiStatus.Internal
    private BlockPosNative() {}

    public static BlockPos convert(net.minecraft.core.BlockPos pos) {
        return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
    }

    public static net.minecraft.core.BlockPos convertFrom(BlockPos pos) {
        return new net.minecraft.core.BlockPos(pos.x(), pos.y(), pos.z());
    }
}
