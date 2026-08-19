package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.world.entity.animal.equine.Llama.Variant;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.HashMap;

@WillRefactor(aspect = "name")
public enum LlamaVariant {
    CREAMY(0, "creamy"),
    WHITE(1, "white"),
    BROWN(2, "brown"),
    GRAY(3, "gray");

    public final int index;
    public final String name;

    LlamaVariant(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static final HashMap<Integer, LlamaVariant> BY_ID;
    static {
        BY_ID = new HashMap<>();

        var members = values();
        for (LlamaVariant member : members)
            BY_ID.put(member.index, member);
    }

    private static final HashMap<LlamaVariant, Variant> REVERSE;
    static {
        REVERSE = new HashMap<>();

        for (Variant member : Variant.values())
            REVERSE.put(BY_ID.get(member.getId()), member);
    }

    public static final class Controller
           implements TransformingItemComponentValue<Variant, LlamaVariant>
    {
        @Override
        public LlamaVariant transform(Variant value) {
            return LlamaVariant.BY_ID.get(value.getId());
        }

        @Override
        public Variant untransform(LlamaVariant value) {
            return REVERSE.get(value);
        }
    }
}
