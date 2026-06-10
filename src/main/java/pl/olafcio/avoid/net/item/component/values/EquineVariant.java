package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.world.entity.animal.equine.Variant;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.HashMap;

@WillRefactor(aspect = "name")
public enum EquineVariant {
    WHITE(0, "white"),
    CREAMY(1, "creamy"),
    CHESTNUT(2, "chestnut"),
    BROWN(3, "brown"),
    BLACK(4, "black"),
    GRAY(5, "gray"),
    DARK_BROWN(6, "dark_brown");

    public final int index;
    public final String name;

    EquineVariant(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static final HashMap<Integer, EquineVariant> BY_ID;
    static {
        BY_ID = new HashMap<>();

        var members = values();
        for (EquineVariant member : members)
            BY_ID.put(member.index, member);
    }

    private static final HashMap<EquineVariant, Variant> REVERSE;
    static {
        REVERSE = new HashMap<>();

        for (Variant member : Variant.values())
            REVERSE.put(BY_ID.get(member.getId()), member);
    }

    public static final class Controller
           implements TransformingItemComponentValue<Variant, EquineVariant>
    {
        @Override
        public EquineVariant transform(Variant value) {
            return EquineVariant.BY_ID.get(value.getId());
        }

        @Override
        public Variant untransform(EquineVariant value) {
            return REVERSE.get(value);
        }
    }
}
