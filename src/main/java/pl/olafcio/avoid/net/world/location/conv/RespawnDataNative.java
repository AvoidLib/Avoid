package pl.olafcio.avoid.net.world.location.conv;

import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.LevelData;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.world.location.Location;

@Native
@ApiStatus.Internal
public final class RespawnDataNative {
    @ApiStatus.Internal
    private RespawnDataNative() {}

    public static LevelData.RespawnData convert(Location loc) {
        return new LevelData.RespawnData(
                new GlobalPos(
                    ResourceKey.create(Registries.DIMENSION, IdentificationNative.convert(loc.world())),
                    BlockPosNative.convertFrom(loc.blockPos())
                ),
                loc.yaw(),
                loc.pitch()
        );
    }

    public static Location convertFrom(LevelData.RespawnData loc) {
        return new Location(
                IdentificationNative.convertFrom(loc.dimension().identifier()),
                BlockPosNative.convert(loc.pos()),
                loc.yaw(),
                loc.pitch()
        );
    }
}
