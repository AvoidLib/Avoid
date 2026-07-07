package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.List;

public record CustomModelData(List<Float> floats, List<Boolean> flags, List<String> strings, List<Integer> colors) {
    public static final class Controller
           implements TransformingItemComponentValue<net.minecraft.world.item.component.CustomModelData, CustomModelData>
    {
        @Override
        public CustomModelData transform(net.minecraft.world.item.component.CustomModelData value) {
            return new CustomModelData(value.floats(), value.flags(), value.strings(), value.colors());
        }

        @Override
        public net.minecraft.world.item.component.CustomModelData untransform(CustomModelData value) {
            return new net.minecraft.world.item.component.CustomModelData(value.floats, value.flags, value.strings, value.colors);
        }
    }
}
