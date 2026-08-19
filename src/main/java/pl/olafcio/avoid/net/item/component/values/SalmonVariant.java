package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.world.entity.animal.fish.Salmon.Variant;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.mixin.accessors.ISalmon$Variant;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.HashMap;

@WillRefactor(aspect = "name")
public enum SalmonVariant {
    SMALL(0, "small"),
    MEDIUM(1, "medium"),
    LARGE(2, "large");

    public final int index;
    public final String name;

    SalmonVariant(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static final HashMap<Integer, SalmonVariant> BY_ID;
    static {
        BY_ID = new HashMap<>();

        var members = values();
        for (SalmonVariant member : members)
            BY_ID.put(member.index, member);
    }

    private static final HashMap<SalmonVariant, Variant> REVERSE;
    static {
        REVERSE = new HashMap<>();

        for (Variant member : Variant.values())
            REVERSE.put(BY_ID.get(((ISalmon$Variant) (Object) member).avoid$id()), member);
    }

    public static final class Controller
           implements TransformingItemComponentValue<Variant, SalmonVariant>
    {
        @Override
        public SalmonVariant transform(Variant value) {
            return SalmonVariant.BY_ID.get(((ISalmon$Variant) (Object) value).avoid$id());
        }

        @Override
        public Variant untransform(SalmonVariant value) {
            return REVERSE.get(value);
        }
    }
}
