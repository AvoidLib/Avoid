package pl.olafcio.avoid.net.fluid;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.fluid.properties._layer;
import pl.olafcio.avoid.net.fluid.properties.layer.ChunkLayerNative;

@Native
@ApiStatus.Internal
class FluidsClientNative {
    @ApiStatus.Internal
    private FluidsClientNative() {}

    static void handleLayer(AvoidFluid source, Class<? extends Fluid> klass, AvoidFluid flowing) {
        ItemBlockRenderTypes.LAYER_BY_FLUID.put(source, ChunkLayerNative.convertFrom(klass.getAnnotation(_layer.class)
                                                                                          .value()));

        ItemBlockRenderTypes.LAYER_BY_FLUID.put(flowing, ChunkLayerNative.convertFrom(klass.getAnnotation(_layer.class)
                                                                                           .value()));
    }
}
