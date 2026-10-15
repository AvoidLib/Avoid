package pl.olafcio.avoid_impl.net.fluid.properties.layer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.fluid.properties.layer.ChunkLayer;

@Native
@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public final class ChunkLayerNative {
    @ApiStatus.Internal
    private ChunkLayerNative() {}

    public static RenderType convertFrom(ChunkLayer layer) {
             if (layer == ChunkLayer.SOLID)       return RenderType.solid();
        else if (layer == ChunkLayer.CUTOUT)      return RenderType.cutout();
        else if (layer == ChunkLayer.TRANSLUCENT) return RenderType.translucent();
        else                                      return RenderType.tripwire();
    }
}
