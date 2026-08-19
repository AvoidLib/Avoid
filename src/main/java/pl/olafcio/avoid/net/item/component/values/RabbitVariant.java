package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.world.entity.animal.rabbit.Rabbit.Variant;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.HashMap;

@WillRefactor(aspect = "name")
public enum RabbitVariant {
    BROWN(0, "brown"),
    WHITE(1, "white"),
    BLACK(2, "black"),
    WHITE_SPLOTCHED(3, "white_splotched"),
    GOLD(4, "gold"),
    SALT(5, "salt"),
    EVIL(99, "evil");

    public final int index;
    public final String name;

    RabbitVariant(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static final HashMap<Integer, RabbitVariant> BY_ID;
    static {
        BY_ID = new HashMap<>();

        var members = values();
        for (RabbitVariant member : members)
            BY_ID.put(member.index, member);
    }

    private static final HashMap<RabbitVariant, Variant> REVERSE;
    static {
        REVERSE = new HashMap<>();

        for (Variant member : Variant.values())
            REVERSE.put(BY_ID.get(member.id()), member);
    }

    public static final class Controller
           implements TransformingItemComponentValue<Variant, RabbitVariant>
    {
        @Override
        public RabbitVariant transform(Variant value) {
            return RabbitVariant.BY_ID.get(value.id());
        }

        @Override
        public Variant untransform(RabbitVariant value) {
            return REVERSE.get(value);
        }
    }

    public static final _value_type<RabbitVariant> TYPE
                  = new _value_type<>(Variant.class, new Controller());
}
