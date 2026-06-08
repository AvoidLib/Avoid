package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public enum MapPostProcessing {
    LOCK(0),
    SCALE(1);

    private final int index;

    MapPostProcessing(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static final class Controller
           implements TransformingItemComponentValue<net.minecraft.world.item.component.MapPostProcessing, MapPostProcessing>
    {
        @Override
        public MapPostProcessing transform(net.minecraft.world.item.component.MapPostProcessing value) {
            return MapPostProcessing.values()[value.ordinal()];
        }

        @Override
        public net.minecraft.world.item.component.MapPostProcessing untransform(MapPostProcessing value) {
            return net.minecraft.world.item.component.MapPostProcessing.values()[value.ordinal()];
        }
    }
}
