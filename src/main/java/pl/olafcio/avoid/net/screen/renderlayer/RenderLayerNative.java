package pl.olafcio.avoid.net.screen.renderlayer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class RenderLayerNative {
    @ApiStatus.Internal
    private RenderLayerNative() {}

    public static RenderPipeline convert(RenderLayer layer) {
        return layer.get();
    }
}
