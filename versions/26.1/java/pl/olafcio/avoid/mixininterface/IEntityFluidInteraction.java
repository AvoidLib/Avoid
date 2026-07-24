package pl.olafcio.avoid.mixininterface;

import pl.olafcio.avoid.net.fluid.Fluid;

public interface IEntityFluidInteraction {
    double avoid$getFluidHeight(final Fluid fluid);
    boolean avoid$isInFluid(final Fluid fluid);
    boolean avoid$isEyeInFluid(final Fluid fluid);
}
