package pl.olafcio.avoid.net.block;

import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import java.util.ArrayList;

@Native
@ApiStatus.Internal
public final class BlocksNative {
    @ApiStatus.Internal
    private BlocksNative() {}

    public static ArrayList<Block> blocks
            = new ArrayList<>();
}
