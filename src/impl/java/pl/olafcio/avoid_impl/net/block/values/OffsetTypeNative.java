package pl.olafcio.avoid_impl.net.block.values;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.block.values.OffsetType;

@Native
@ApiStatus.Internal
public final class OffsetTypeNative {
    @ApiStatus.Internal
    private OffsetTypeNative() {}

    public static BlockBehaviour.OffsetType convert(OffsetType avoid) {
             if (avoid == OffsetType.NONE) return BlockBehaviour.OffsetType.NONE;
        else if (avoid == OffsetType.XZ)   return BlockBehaviour.OffsetType.XZ;
        else if (avoid == OffsetType.XYZ)  return BlockBehaviour.OffsetType.XYZ;
        else
            throw new RuntimeException("Unknown Avoid offset-type '%s'".formatted(avoid));
    }
}
