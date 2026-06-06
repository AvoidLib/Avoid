package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public record MapId(int id) {
    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.level.saveddata.maps.MapId, MapId>
    {
        @Override
        public MapId transform(net.minecraft.world.level.saveddata.maps.MapId value) {
            return new MapId(value.id());
        }

        @Override
        public net.minecraft.world.level.saveddata.maps.MapId untransform(MapId value) {
            return new net.minecraft.world.level.saveddata.maps.MapId(value.id);
        }
    }
}
