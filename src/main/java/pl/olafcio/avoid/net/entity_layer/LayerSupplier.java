package pl.olafcio.avoid.net.entity_layer;

import pl.olafcio.avoid.net.entity_layer.builders.LayerDef;

@FunctionalInterface
public interface LayerSupplier {
    LayerDef make();
}
