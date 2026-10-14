package pl.olafcio.avoid.net.fluid;

import pl.olafcio.avoid.net.id.Identification;

/**
 * A namespace used to manage fluids.
 * <br/><br/>
 * To register a fluid, see: {@link #register(Identification, Fluid) register([Identification] id, [Fluid] fluid)}
 */
public final class Fluids {
    private Fluids() {}

    /**
     * Creates a new fluid type.
     */
    public static void register(Identification id, Fluid fluid) {
        pl.olafcio.avoid_impl.net.fluid.Fluids.register(id, fluid);
    }
}
