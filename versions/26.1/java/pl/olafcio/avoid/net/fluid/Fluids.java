package pl.olafcio.avoid.net.fluid;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.FluidState;
import pl.olafcio.avoid.net.fluid.properties._layer;
import pl.olafcio.avoid.net.fluid.properties.layer.ChunkLayerNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;

public final class Fluids {
    private Fluids() {}

    public static void register(Identification id, Fluid fluid) {
        AvoidFluid source;
        AvoidFluid flowing;

        Identifier sourceID = Identifier.fromNamespaceAndPath(id.namespace(), id.path());
        Identifier flowingID = Identifier.fromNamespaceAndPath(id.namespace(), "flowing_" + id.path());

        Registry.register(BuiltInRegistries.FLUID, sourceID,  source  = new AvoidFluid.Source(fluid));
        Registry.register(BuiltInRegistries.FLUID, flowingID, flowing = new AvoidFluid.Flowing(fluid));

        source.source  = source;
        source.flowing = flowing;

        flowing.source  = source;
        flowing.flowing = flowing;

        fluid.id = IdentificationNative.convertFrom(BuiltInRegistries.FLUID.getKey(source));
        fluid.fluid = source;

        FluidsNative.classes.put(fluid.getClass(), source);
        FluidsNative.instances.put(fluid, source);

        for (FluidState fluidState : source.getStateDefinition().getPossibleStates()) {
            net.minecraft.world.level.material.Fluid.FLUID_STATE_REGISTRY.add(fluidState);
        }

        for (FluidState fluidState : flowing.getStateDefinition().getPossibleStates()) {
            net.minecraft.world.level.material.Fluid.FLUID_STATE_REGISTRY.add(fluidState);
        }
    }
}
