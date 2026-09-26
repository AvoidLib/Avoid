package pl.olafcio.avoid_impl.net.world.block_data;

import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class BlockDataNative {
    @ApiStatus.Internal
    private BlockDataNative() {}

    public static BlockState convert(pl.olafcio.avoid.net.world.block_data.BlockData data) {
        return ((BlockData) data).state;
    }

    public static pl.olafcio.avoid.net.world.block_data.BlockData convertFrom(BlockState state) {
        return new BlockData(state);
    }
}
