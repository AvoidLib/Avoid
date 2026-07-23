package pl.olafcio.avoid.net.fluid;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import pl.olafcio.avoid.net.fluid.properties._layer;
import pl.olafcio.avoid.net.fluid.properties.layer.ChunkLayerNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;

import java.util.HashMap;

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

        instances.put(fluid.getClass(), source);

        var klass = fluid.getClass();
        if (klass.isAnnotationPresent(_layer.class)) {
            ItemBlockRenderTypes.LAYER_BY_FLUID.put(source, ChunkLayerNative.convertFrom(klass.getAnnotation(_layer.class)
                                                                                              .value()));

            ItemBlockRenderTypes.LAYER_BY_FLUID.put(flowing, ChunkLayerNative.convertFrom(klass.getAnnotation(_layer.class)
                                                                                               .value()));
        }
    }

    static final HashMap<Class<? extends Fluid>, AvoidFluid> instances
           = new HashMap<>();
}
