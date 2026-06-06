package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public record MapItemColor(int rgb) {
    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.component.MapItemColor, MapItemColor>
    {
        @Override
        public MapItemColor transform(net.minecraft.world.item.component.MapItemColor value) {
            return new MapItemColor(value.rgb());
        }

        @Override
        public net.minecraft.world.item.component.MapItemColor untransform(MapItemColor value) {
            return new net.minecraft.world.item.component.MapItemColor(value.rgb);
        }
    }
}
