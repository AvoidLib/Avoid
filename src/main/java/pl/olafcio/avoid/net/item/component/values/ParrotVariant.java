package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.world.entity.animal.parrot.Parrot.Variant;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.HashMap;

@WillRefactor(aspect = "name")
public enum ParrotVariant {
    RED_BLUE(0, "red_blue"),
    BLUE(1, "blue"),
    GREEN(2, "green"),
    YELLOW_BLUE(3, "yellow_blue"),
    GRAY(4, "gray");

    public final int index;
    public final String name;

    ParrotVariant(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static final HashMap<Integer, ParrotVariant> BY_ID;
    static {
        BY_ID = new HashMap<>();

        var members = values();
        for (ParrotVariant member : members)
            BY_ID.put(member.index, member);
    }

    private static final HashMap<ParrotVariant, Variant> REVERSE;
    static {
        REVERSE = new HashMap<>();

        for (Variant member : Variant.values())
            REVERSE.put(BY_ID.get(member.getId()), member);
    }

    public static final class Controller
           implements TransformingItemComponentValue<Variant, ParrotVariant>
    {
        @Override
        public ParrotVariant transform(Variant value) {
            return ParrotVariant.BY_ID.get(value.getId());
        }

        @Override
        public Variant untransform(ParrotVariant value) {
            return REVERSE.get(value);
        }
    }

    public static final _value_type<ParrotVariant> TYPE
                  = new _value_type<>(Variant.class, new Controller());
}
