package pl.olafcio.avoid.net.entity_layer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import pl.olafcio.avoid.net._3d.layer.LayerDef;

@FunctionalInterface
@Environment(EnvType.CLIENT)
public interface LayerSupplier {
    LayerDef make();
}
