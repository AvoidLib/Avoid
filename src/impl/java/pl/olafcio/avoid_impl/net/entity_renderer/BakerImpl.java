package pl.olafcio.avoid_impl.net.entity_renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.intellij.lang.annotations.MagicConstant;
import pl.olafcio.avoid.annotations.dist.MCEnvironment;
import pl.olafcio.avoid.net._3d.model.ModelPart;
import pl.olafcio.avoid_impl.net._3d.model.ModelPartNative;
import pl.olafcio.avoid.net.entity_layer.Element;
import pl.olafcio.avoid.net.entity_layer.LayerSupplier;
import pl.olafcio.avoid.net.entity_renderer.Baker;
import pl.olafcio.avoid.net.id.Identification;

public final class BakerImpl extends Baker {
    private final Object ctx;

    BakerImpl(Object ctx) {
        this.ctx = ctx;
    }

    @MCEnvironment("client")
    private EntityRendererProvider.Context ctx() {
        return (EntityRendererProvider.Context) ctx;
    }

    @MCEnvironment("client")
    public ModelPart bakeLayer(Class<? extends LayerSupplier> layerClass) {
        return ModelPartNative.convert(ctx().bakeLayer(BakerNative.LOCATIONS.get(layerClass)));
    }

    @MCEnvironment("client")
    public ModelPart bakeLayer(Identification id, @MagicConstant(valuesFromClass = Element.class) String element) {
        return bakeLayer(id.toString(), element);
    }

    @MCEnvironment("client")
    public ModelPart bakeLayer(String id, @MagicConstant(valuesFromClass = Element.class) String element) {
        if (id.indexOf(':') == -1)
            id = "minecraft:" + id;

        return ModelPartNative.convert(ctx().bakeLayer(BakerNative.LOCATIONS.get(id + "#" + element)));
    }

    @MCEnvironment("client")
    public ModelPart bakeLayer(String id) {
        return bakeLayer(id, Element.MAIN);
    }
}
