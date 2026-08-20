package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.core.registries.BuiltInRegistries;
import pl.olafcio.avoid.internal.VResourceKey;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.HashMap;
import java.util.Map;

public record MapDecorations(Map<String, Entry> decorations) {
    public record Entry(Identification type, double x, double z, float rotation) {}

    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.component.MapDecorations, MapDecorations>
    {
        @Override
        public MapDecorations transform(net.minecraft.world.item.component.MapDecorations value) {
            var src = value.decorations();
            var dst = new HashMap<String, Entry>(src.size());

            for (var entry : src.entrySet()) {
                var key = entry.getKey();
                var val = entry.getValue();

                dst.put(key, new Entry(
                        IdentificationNative.convertFrom(VResourceKey.identifier(val.type().unwrapKey().orElseThrow())),
                        val.x(), val.z(),
                        val.rotation()
                ));
            }

            return new MapDecorations(dst);
        }

        @Override
        public net.minecraft.world.item.component.MapDecorations untransform(MapDecorations value) {
            var src = value.decorations();
            var dst = new HashMap<String, net.minecraft.world.item.component.MapDecorations.Entry>(src.size());

            for (var entry : src.entrySet()) {
                var key = entry.getKey();
                var val = entry.getValue();

                dst.put(key, new net.minecraft.world.item.component.MapDecorations.Entry(
                        BuiltInRegistries.MAP_DECORATION_TYPE.wrapAsHolder(BuiltInRegistries.MAP_DECORATION_TYPE.get(IdentificationNative.convert(val.type())).orElseThrow().value()),
                        val.x(), val.z(),
                        val.rotation()
                ));
            }

            return new net.minecraft.world.item.component.MapDecorations(dst);
        }
    }

    public static final _value_type<MapDecorations> TYPE
                  = new _value_type<>(net.minecraft.world.item.component.MapDecorations.class, new Controller());
}
