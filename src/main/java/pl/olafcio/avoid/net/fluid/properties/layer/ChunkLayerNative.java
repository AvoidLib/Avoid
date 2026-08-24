package pl.olafcio.avoid.net.fluid.properties.layer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public final class ChunkLayerNative {
    @ApiStatus.Internal
    private ChunkLayerNative() {}

    public static ChunkSectionLayer convertFrom(ChunkLayer layer) {
             if (layer == ChunkLayer.SOLID)       return ChunkSectionLayer.SOLID;
        else if (layer == ChunkLayer.CUTOUT)      return ChunkSectionLayer.CUTOUT;
        else if (layer == ChunkLayer.TRANSLUCENT) return ChunkSectionLayer.TRANSLUCENT;
        else                                      return ChunkSectionLayer.TRIPWIRE;
    }
}
