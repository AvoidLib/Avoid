package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;
import pl.olafcio.avoid.net.item.component.internal.Poly_SwingAnimation;

public record SwingAnimation(SwingAnimationType type, int duration) {
    public static final SwingAnimation DEFAULT
                  = new SwingAnimation(SwingAnimationType.WHACK, 6);

    public static final class Controller
            implements TransformingItemComponentValue<Poly_SwingAnimation, SwingAnimation>
    {
        @Override
        public SwingAnimation transform(Poly_SwingAnimation value) {
            return new SwingAnimation(value.type(), value.duration());
        }

        @Override
        public Poly_SwingAnimation untransform(SwingAnimation value) {
            return new Poly_SwingAnimation(value.type, value.duration);
        }
    }

    public static final _value_type<SwingAnimation> TYPE
                  = new _value_type<>(Poly_SwingAnimation.class, new Controller());
}
