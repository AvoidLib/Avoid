package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public record FoodProperties(int nutrition, float saturation, boolean canAlwaysEat) {
    public static final class Controller
           implements TransformingItemComponentValue<net.minecraft.world.food.FoodProperties, FoodProperties>
    {
        @Override
        public FoodProperties transform(net.minecraft.world.food.FoodProperties value) {
            return new FoodProperties(value.nutrition(), value.saturation(), value.canAlwaysEat());
        }

        @Override
        public net.minecraft.world.food.FoodProperties untransform(FoodProperties value) {
            return new net.minecraft.world.food.FoodProperties(value.nutrition, value.saturation, value.canAlwaysEat);
        }
    }
}
