package pl.olafcio.avoid.net.entity_renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.intellij.lang.annotations.MagicConstant;
import pl.olafcio.avoid.net._3d.model.ModelPart;
import pl.olafcio.avoid.net.entity_layer.Element;
import pl.olafcio.avoid.net.entity_layer.LayerSupplier;
import pl.olafcio.avoid.net.id.Identification;

public abstract class Baker {
    @Environment(EnvType.CLIENT)
    public abstract ModelPart bakeLayer(Class<? extends LayerSupplier> layerClass);

    @Environment(EnvType.CLIENT)
    public abstract ModelPart bakeLayer(Identification id, @MagicConstant(valuesFromClass = Element.class) String element);

    @Environment(EnvType.CLIENT)
    public abstract ModelPart bakeLayer(String id, @MagicConstant(valuesFromClass = Element.class) String element);

    @Environment(EnvType.CLIENT)
    public abstract ModelPart bakeLayer(String id);
}
