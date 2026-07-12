package pl.olafcio.avoid.net.entity_renderer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import java.util.HashMap;

@Native
@ApiStatus.Internal
public final class BakerNative {
    @ApiStatus.Internal
    private BakerNative() {}

    public static HashMap<Object, ModelLayerLocation> LOCATIONS;
}
