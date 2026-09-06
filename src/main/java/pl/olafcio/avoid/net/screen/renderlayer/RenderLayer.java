package pl.olafcio.avoid.net.screen.renderlayer;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
@ApiStatus.Experimental
public abstract class RenderLayer {
    public abstract String getName();
    public abstract boolean isPresent();

    abstract Object get();
}
