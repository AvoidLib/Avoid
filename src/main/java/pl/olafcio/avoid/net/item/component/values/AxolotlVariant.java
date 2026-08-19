package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.world.entity.animal.axolotl.Axolotl.Variant;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.HashMap;

@WillRefactor(aspect = "name")
public enum AxolotlVariant {
    LUCY(0, "lucy"),
    WILD(1, "wild"),
    GOLD(2, "gold"),
    CYAN(3, "cyan"),
    BLUE(4, "blue");

    public final int index;
    public final String name;

    AxolotlVariant(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static final HashMap<Integer, AxolotlVariant> BY_ID;
    static {
        BY_ID = new HashMap<>();

        var members = values();
        for (AxolotlVariant member : members)
            BY_ID.put(member.index, member);
    }

    private static final HashMap<AxolotlVariant, Variant> REVERSE;
    static {
        REVERSE = new HashMap<>();

        for (Variant member : Variant.values())
            REVERSE.put(BY_ID.get(member.getId()), member);
    }

    public static final class Controller
           implements TransformingItemComponentValue<Variant, AxolotlVariant>
    {
        @Override
        public AxolotlVariant transform(Variant value) {
            return AxolotlVariant.BY_ID.get(value.getId());
        }

        @Override
        public Variant untransform(AxolotlVariant value) {
            return REVERSE.get(value);
        }
    }

    public static final _value_type<AxolotlVariant> TYPE
                  = new _value_type<>(Variant.class, new Controller());
}
