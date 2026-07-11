package pl.olafcio.avoid.net.entity_layer;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.id.Identification;

/**
 * An entity layer.
 * @param id The layer ID - e.g. {@code avoidtestproject:herobrine}.
 * @param element The layer element - usually {@code main}.
 * @param supplier The layer supplier - a method, that takes no parameters, that returns a LayerDef.
 */
@ApiStatus.Experimental
public record EntityLayer(
        Identification id,
        @MagicConstant(valuesFromClass = Element.class) String element,
        LayerSupplier supplier
) {
    public EntityLayer(Identification id, LayerSupplier supplier) {
        this(id, Element.MAIN, supplier);
    }
}
