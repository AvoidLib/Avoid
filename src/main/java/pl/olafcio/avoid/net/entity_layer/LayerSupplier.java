package pl.olafcio.avoid.net.entity_layer;

import pl.olafcio.avoid.annotations.dist.MCEnvironment;
import pl.olafcio.avoid.net._3d.layer.LayerDef;

@FunctionalInterface
@MCEnvironment("client")
public interface LayerSupplier {
    LayerDef make();
}
