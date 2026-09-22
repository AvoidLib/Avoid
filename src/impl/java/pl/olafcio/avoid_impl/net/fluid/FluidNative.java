package pl.olafcio.avoid_impl.net.fluid;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.fluid.Fluid;

@Native
@ApiStatus.Internal
public final class FluidNative {
    @ApiStatus.Internal
    private FluidNative() {}

    public static Fluid get(AvoidFluid wrapper) {
        return wrapper.fluid;
    }
}
