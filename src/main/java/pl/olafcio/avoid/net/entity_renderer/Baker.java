package pl.olafcio.avoid.net.entity_renderer;

import org.intellij.lang.annotations.MagicConstant;
import pl.olafcio.avoid.annotations.dist.MCEnvironment;
import pl.olafcio.avoid.net._3d.model.ModelPart;
import pl.olafcio.avoid.net.entity_layer.Element;
import pl.olafcio.avoid.net.entity_layer.LayerSupplier;
import pl.olafcio.avoid.net.id.Identification;

public abstract class Baker {
    @MCEnvironment("client")
    public abstract ModelPart bakeLayer(Class<? extends LayerSupplier> layerClass);

    @MCEnvironment("client")
    public abstract ModelPart bakeLayer(Identification id, @MagicConstant(valuesFromClass = Element.class) String element);

    @MCEnvironment("client")
    public abstract ModelPart bakeLayer(String id, @MagicConstant(valuesFromClass = Element.class) String element);

    @MCEnvironment("client")
    public abstract ModelPart bakeLayer(String id);
}
