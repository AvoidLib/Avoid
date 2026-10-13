package pl.olafcio.avoid_impl.net.fluid;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.FluidState;
import pl.olafcio.avoid.net.fluid.properties._layer;
import pl.olafcio.avoid_impl.net.fluid.properties.layer.ChunkLayerNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;
import pl.olafcio.avoid.net.fluid.Fluid;
import pl.olafcio.avoid.net.fluid.FluidStateAccessor;

public final class Fluids {
    private Fluids() {}

    public static void register(Identification id, Fluid fluid) {
        AvoidFluid source;
        Identifier sourceID = Identifier.fromNamespaceAndPath(id.namespace(), id.path());

        Registry.register(BuiltInRegistries.FLUID, sourceID, source = new AvoidFluid(fluid));

        FluidStateAccessor.setID(fluid, IdentificationNative.convertFrom(BuiltInRegistries.FLUID.getKey(source)));
        FluidStateAccessor.setFluid(fluid, source);

        FluidsNative.classes.put(fluid.getClass(), source);
        FluidsNative.instances.put(fluid, source);

        for (FluidState fluidState : source.getStateDefinition().getPossibleStates()) {
            net.minecraft.world.level.material.Fluid.FLUID_STATE_REGISTRY.add(fluidState);
        }
    }
}
