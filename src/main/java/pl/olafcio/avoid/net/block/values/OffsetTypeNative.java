package pl.olafcio.avoid.net.block.values;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class OffsetTypeNative {
    @ApiStatus.Internal
    private OffsetTypeNative() {}

    public static BlockBehaviour.OffsetType convert(OffsetType avoid) {
        return avoid.object;
    }
}
