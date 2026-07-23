package pl.olafcio.avoid.net.fluid;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class FluidsNative {
    @ApiStatus.Internal
    private FluidsNative() {}

    public static AvoidFluid convertFrom(Class<? extends Fluid> fluid) {
        return Fluids.instances.get(fluid);
    }
}
