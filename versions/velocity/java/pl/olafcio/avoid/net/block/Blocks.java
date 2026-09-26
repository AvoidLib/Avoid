package pl.olafcio.avoid.net.block;

import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid_impl.mods.loader.AvoidPackageOnly;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.world.block_data.BlockData;

import java.util.function.Supplier;

@ApiStatus.Experimental
public final class Blocks {
    @ApiStatus.Internal
    private Blocks() {}

    /**
     * Creates a default instance of the given block ID.
     */
    public static BlockData create(Identification id) {
        throw new UnsupportedOperationException("Cannot create block instances on Velocity");
    }

    public static void register(Identification blockID, Supplier<? extends pl.olafcio.avoid.net.block.Block> constructor) {
        throw new UnsupportedOperationException("Cannot register blocks on Velocity");
    }
}
