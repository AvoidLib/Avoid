package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public record DyedItemColor(int rgb) {
    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.component.DyedItemColor, DyedItemColor>
    {
        @Override
        public DyedItemColor transform(net.minecraft.world.item.component.DyedItemColor value) {
            return new DyedItemColor(value.rgb());
        }

        @Override
        public net.minecraft.world.item.component.DyedItemColor untransform(DyedItemColor value) {
            return new net.minecraft.world.item.component.DyedItemColor(value.rgb);
        }
    }

    public static final _value_type<DyedItemColor> TYPE
                  = new _value_type<>(net.minecraft.world.item.component.DyedItemColor.class, new Controller());
}
