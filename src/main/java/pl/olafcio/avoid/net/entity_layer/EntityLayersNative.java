package pl.olafcio.avoid.net.entity_layer;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import java.util.List;

@Native
@ApiStatus.Internal
public final class EntityLayersNative {
    @ApiStatus.Internal
    private EntityLayersNative() {}

    @ApiStatus.Internal
    public static List<EntityLayer> getAndFreeze() {
        return EntityLayers.getAndFreeze();
    }
}
