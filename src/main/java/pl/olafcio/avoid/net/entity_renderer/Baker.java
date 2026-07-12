package pl.olafcio.avoid.net.entity_renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import pl.olafcio.avoid.net._3d.model.ModelPart;
import pl.olafcio.avoid.net._3d.model.ModelPartNative;
import pl.olafcio.avoid.net.entity_layer.LayerSupplier;

public final class Baker {
    private final EntityRendererProvider.Context ctx;

    Baker(EntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }

    public ModelPart bakeLayer(Class<? extends LayerSupplier> layerClass) {
        return ModelPartNative.convert(ctx.bakeLayer(BakerNative.LOCATIONS.get(layerClass)));
    }

    public ModelPart bakeLayer(String id, String element) {
        return ModelPartNative.convert(ctx.bakeLayer(BakerNative.LOCATIONS.get(id + "/" + element)));
    }
}
