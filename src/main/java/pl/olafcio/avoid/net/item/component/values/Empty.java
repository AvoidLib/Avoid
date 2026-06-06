package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.util.Unit;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public final class Empty {
    private static final Empty INSTANCE
                   = new Empty();

    private Empty() {}

    public static final class Controller
           implements TransformingItemComponentValue<Unit, Empty>
    {
        @Override
        public Empty transform(Unit value) {
            return INSTANCE;
        }

        @Override
        public Unit untransform(Empty value) {
            return Unit.INSTANCE;
        }
    }
}
