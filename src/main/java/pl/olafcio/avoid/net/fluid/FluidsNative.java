package pl.olafcio.avoid.net.fluid;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import java.util.HashMap;

@Native
@ApiStatus.Internal
public final class FluidsNative {
    static final HashMap<Class<? extends Fluid>, AvoidFluid> classes
           = new HashMap<>();

    public static final HashMap<Fluid, AvoidFluid> instances
                  = new HashMap<>();

    @ApiStatus.Internal
    private FluidsNative() {}

    public static AvoidFluid convertFrom(Class<? extends Fluid> fluid) {
        return classes.get(fluid);
    }
}
