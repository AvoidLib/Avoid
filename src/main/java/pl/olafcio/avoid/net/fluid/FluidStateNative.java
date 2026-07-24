package pl.olafcio.avoid.net.fluid;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import java.util.HashMap;

@Native
@ApiStatus.Internal
public final class FluidStateNative {
    @ApiStatus.Internal
    private FluidStateNative() {}

    public static FluidState create(net.minecraft.world.level.material.FluidState state) {
        return new FluidState(state);
    }
}
