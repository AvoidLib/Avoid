package pl.olafcio.avoid.net.world.block_location;

import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.internal.VResourceKey;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.id.IdentificationNative;

@Native
@ApiStatus.Internal
public final class BlockLocationNative {
    @ApiStatus.Internal
    private BlockLocationNative() {}

    public static GlobalPos convert(BlockLocation loc) {
        return new GlobalPos(
                ResourceKey.create(Registries.DIMENSION, IdentificationNative.convert(loc.world())),
                BlockPosNative.convertFrom(loc.blockPos())
        );
    }

    public static BlockLocation convertFrom(GlobalPos loc) {
        return new BlockLocation(
                IdentificationNative.convertFrom(VResourceKey.identifier(loc.dimension())),
                BlockPosNative.convert(loc.pos())
        );
    }
}
