package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.world.entity.animal.fox.Fox.Variant;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.HashMap;

@WillRefactor(aspect = "name")
public enum FoxVariant {
    RED(0, "red"),
    SNOW(1, "snow");

    public final int index;
    public final String name;

    FoxVariant(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static final HashMap<Integer, FoxVariant> BY_ID;
    static {
        BY_ID = new HashMap<>();

        var members = values();
        for (FoxVariant member : members)
            BY_ID.put(member.index, member);
    }

    private static final HashMap<FoxVariant, Variant> REVERSE;
    static {
        REVERSE = new HashMap<>();

        for (Variant member : Variant.values())
            REVERSE.put(BY_ID.get(member.getId()), member);
    }

    public static final class Controller
           implements TransformingItemComponentValue<Variant, FoxVariant>
    {
        @Override
        public FoxVariant transform(Variant value) {
            return FoxVariant.BY_ID.get(value.getId());
        }

        @Override
        public Variant untransform(FoxVariant value) {
            return REVERSE.get(value);
        }
    }
}
