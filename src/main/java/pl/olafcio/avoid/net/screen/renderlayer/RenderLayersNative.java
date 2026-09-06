package pl.olafcio.avoid.net.screen.renderlayer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import java.util.HashMap;

@Native
@ApiStatus.Internal
final class RenderLayersNative {
    static HashMap<String, RenderPipeline> PRESENT;

    @ApiStatus.Internal
    private RenderLayersNative() {}
}
