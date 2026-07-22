package pl.olafcio.avoid.net.block.values;

import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import java.util.HashMap;

@Native
@ApiStatus.Internal
public final class MapColorNative {
    @ApiStatus.Internal
    private MapColorNative() {}

    public static final HashMap<String, MapColor> NAME_TO_MAP;

    static {
        NAME_TO_MAP = new HashMap<>();

        var klass = pl.olafcio.avoid.net.block.values.MapColor.class;
        var fields = klass.getDeclaredFields();

        for (var f : fields) {
            if (!klass.isAssignableFrom(f.getType()))
                continue;

            try {
                var avoid = (pl.olafcio.avoid.net.block.values.MapColor) f.get(null);
                var craft = MapColor.byId(avoid.id());

                NAME_TO_MAP.put(f.getName(), craft);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to process mapcolor '%s'".formatted(f.getName()), e);
            }
        }
    }
}
