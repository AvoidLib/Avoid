package pl.olafcio.avoid.net.fluid.properties.layer;

import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class ChunkLayerNative {
    @ApiStatus.Internal
    private ChunkLayerNative() {}

    public static ChunkSectionLayer convertFrom(ChunkLayer layer) {
        return layer.object;
    }
}
