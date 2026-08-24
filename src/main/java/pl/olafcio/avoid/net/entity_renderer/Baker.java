package pl.olafcio.avoid.net.entity_renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.intellij.lang.annotations.MagicConstant;
import pl.olafcio.avoid.net._3d.model.ModelPart;
import pl.olafcio.avoid.net._3d.model.ModelPartNative;
import pl.olafcio.avoid.net.entity_layer.Element;
import pl.olafcio.avoid.net.entity_layer.LayerSupplier;
import pl.olafcio.avoid.net.id.Identification;

public final class Baker {
    private final Object ctx;

    Baker(Object ctx) {
        this.ctx = ctx;
    }

    @Environment(EnvType.CLIENT)
    private EntityRendererProvider.Context ctx() {
        return (EntityRendererProvider.Context) ctx;
    }

    @Environment(EnvType.CLIENT)
    public ModelPart bakeLayer(Class<? extends LayerSupplier> layerClass) {
        return ModelPartNative.convert(ctx().bakeLayer(BakerNative.LOCATIONS.get(layerClass)));
    }

    @Environment(EnvType.CLIENT)
    public ModelPart bakeLayer(Identification id, @MagicConstant(valuesFromClass = Element.class) String element) {
        return bakeLayer(id.toString(), element);
    }

    @Environment(EnvType.CLIENT)
    public ModelPart bakeLayer(String id, @MagicConstant(valuesFromClass = Element.class) String element) {
        if (id.indexOf(':') == -1)
            id = "minecraft:" + id;

        return ModelPartNative.convert(ctx().bakeLayer(BakerNative.LOCATIONS.get(id + "#" + element)));
    }

    @Environment(EnvType.CLIENT)
    public ModelPart bakeLayer(String id) {
        return bakeLayer(id, Element.MAIN);
    }
}
