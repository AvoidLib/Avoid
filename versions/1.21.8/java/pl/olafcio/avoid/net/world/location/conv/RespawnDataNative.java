package pl.olafcio.avoid.net.world.location.conv;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.internal.VResourceKey;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.world.location.Location;

@Native
@ApiStatus.Internal
public final class RespawnDataNative {
    @ApiStatus.Internal
    private RespawnDataNative() {}

    public static ResourceKey<Level> convertDimension(Location location) {
        return ResourceKey.create(Registries.DIMENSION, IdentificationNative.convert(location.world()));
    }

    public static BlockPos convertBlockPos(Location location) {
        return BlockPosNative.convertFrom(location.blockPos());
    }

    public static Location convertFrom(BlockPos pos, ResourceKey<Level> dimension, float angle) {
        return new Location(
                IdentificationNative.convertFrom(VResourceKey.identifier(dimension)),
                BlockPosNative.convert(pos),
                angle,
                0
        );
    }
}
