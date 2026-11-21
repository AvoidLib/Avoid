package pl.olafcio.avoid_impl.net.fluid.properties.layer;

import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.annotations.dist.MCEnvironment;
import pl.olafcio.avoid.net.fluid.properties.layer.ChunkLayer;

@Native
@MCEnvironment("client")
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
