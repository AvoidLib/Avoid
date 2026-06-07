package pl.olafcio.avoid.net.screen.renderlayer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@ApiStatus.Experimental
public abstract class RenderLayer {
    public abstract String getName();
    public abstract boolean isPresent();

    abstract RenderPipeline get();
}
