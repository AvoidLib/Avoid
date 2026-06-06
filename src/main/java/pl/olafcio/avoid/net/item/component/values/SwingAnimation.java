package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public record SwingAnimation(SwingAnimationType type, int duration) {
    public static final SwingAnimation DEFAULT
                  = new SwingAnimation(SwingAnimationType.WHACK, 6);

    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.component.SwingAnimation, SwingAnimation>
    {
        @Override
        public SwingAnimation transform(net.minecraft.world.item.component.SwingAnimation value) {
            return new SwingAnimation(SwingAnimationType.REV_LOOKUP.get(value.type()), value.duration());
        }

        @Override
        public net.minecraft.world.item.component.SwingAnimation untransform(SwingAnimation value) {
            return new net.minecraft.world.item.component.SwingAnimation(SwingAnimationType.LOOKUP.get(value.type), value.duration);
        }
    }
}
