package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.world.entity.animal.cow.MushroomCow.Variant;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.mixin.accessors.IMushroomCow$Variant;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.HashMap;

@WillRefactor(aspect = "name")
public enum MushroomCowVariant {
    RED(0, "red"),
    BROWN(1, "brown");

    public final int index;
    public final String name;

    MushroomCowVariant(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static final HashMap<Integer, MushroomCowVariant> BY_ID;
    static {
        BY_ID = new HashMap<>();

        var members = values();
        for (MushroomCowVariant member : members)
            BY_ID.put(member.index, member);
    }

    private static final HashMap<MushroomCowVariant, Variant> REVERSE;
    static {
        REVERSE = new HashMap<>();

        for (Variant member : Variant.values())
            REVERSE.put(BY_ID.get(((IMushroomCow$Variant) (Object) member).avoid$id()), member);
    }

    public static final class Controller
           implements TransformingItemComponentValue<Variant, MushroomCowVariant>
    {
        @Override
        public MushroomCowVariant transform(Variant value) {
            return MushroomCowVariant.BY_ID.get(((IMushroomCow$Variant) (Object) value).avoid$id());
        }

        @Override
        public Variant untransform(MushroomCowVariant value) {
            return REVERSE.get(value);
        }
    }

    public static final _value_type<MushroomCowVariant> TYPE
                  = new _value_type<>(Variant.class, new Controller());
}
