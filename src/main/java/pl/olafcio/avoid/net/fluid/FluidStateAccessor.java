package pl.olafcio.avoid.net.fluid;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.Discouraged;
import pl.olafcio.avoid.net.id.Identification;

@Discouraged(reason = "This may be revamped")
@ApiStatus.Experimental
public final class FluidStateAccessor {
    @ApiStatus.Internal
    private FluidStateAccessor() {}

    public static void setFluid(Fluid fluid, net.minecraft.world.level.material.Fluid value) {
        fluid.fluid = value;
    }

    public static void setID(Fluid fluid, Identification value) {
        fluid.id = value;
    }

    public static float getExplosionResistance(Fluid fluid) {
        return fluid.getExplosionResistance();
    }
}
