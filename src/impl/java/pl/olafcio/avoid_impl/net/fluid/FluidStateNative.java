package pl.olafcio.avoid_impl.net.fluid;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class FluidStateNative {
    @ApiStatus.Internal
    private FluidStateNative() {}

    public static FluidState create(net.minecraft.world.level.material.FluidState state) {
        return new FluidState(state);
    }

    public static net.minecraft.world.level.material.FluidState convert(pl.olafcio.avoid.net.fluid.FluidState state) {
        return ((FluidState) state).state;
    }
}
