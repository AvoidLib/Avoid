package pl.olafcio.avoid.net.fluid;

import pl.olafcio.avoid.net.id.Identification;

public final class Fluids {
    private Fluids() {}

    public static void register(Identification id, Fluid fluid) {
        pl.olafcio.avoid_impl.net.fluid.Fluids.register(id, fluid);
    }
}
