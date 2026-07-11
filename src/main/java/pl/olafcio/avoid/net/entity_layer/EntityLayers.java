package pl.olafcio.avoid.net.entity_layer;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Experimental
public final class EntityLayers {
    private static List<EntityLayer> LAYERS
             = new ArrayList<>();

    private static boolean frozen = false;

    @ApiStatus.Experimental
    private EntityLayers() {}

    public static void register(EntityLayer supplier) {
        if (frozen)
            throw new TooLateRegisterException("[EntityLayers#register] EntityLayers were already frozen");

        LAYERS.add(supplier);
    }

    public static List<EntityLayer> getAll() {
        if (!frozen)
            throw new UnsupportedOperationException("[EntityLayers#getAll] This operation is not permitted while the registry is not yet frozen");

        return LAYERS;
    }

    @ApiStatus.Internal
    static List<EntityLayer> getAndFreeze() {
        var val = LAYERS;

        frozen = true;
        LAYERS = ImmutableList.copyOf(LAYERS);

        return val;
    }
}
