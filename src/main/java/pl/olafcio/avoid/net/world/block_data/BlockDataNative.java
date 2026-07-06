package pl.olafcio.avoid.net.world.block_data;

import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class BlockDataNative {
    @ApiStatus.Internal
    private BlockDataNative() {}

    public static BlockData convertFrom(BlockState state) {
        return new BlockData(state);
    }
}
