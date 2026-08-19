package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.Map;

public record BlockItemStateProperties(Map<String, String> properties) {
    public static final class Controller
                        implements TransformingItemComponentValue<net.minecraft.world.item.component.BlockItemStateProperties, BlockItemStateProperties>
    {
        @Override
        public BlockItemStateProperties transform(net.minecraft.world.item.component.BlockItemStateProperties value) {
            return new BlockItemStateProperties(value.properties());
        }

        @Override
        public net.minecraft.world.item.component.BlockItemStateProperties untransform(BlockItemStateProperties value) {
            return new net.minecraft.world.item.component.BlockItemStateProperties(value.properties);
        }
    }
}
