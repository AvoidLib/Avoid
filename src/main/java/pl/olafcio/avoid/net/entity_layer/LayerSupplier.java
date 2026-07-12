package pl.olafcio.avoid.net.entity_layer;

import pl.olafcio.avoid.net._3d.layer.LayerDef;

@FunctionalInterface
public interface LayerSupplier {
    LayerDef make();
}
