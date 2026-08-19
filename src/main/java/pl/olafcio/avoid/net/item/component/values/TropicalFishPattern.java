package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.world.entity.animal.fish.TropicalFish;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.mixin.accessors.ITropicalFish$Base;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.HashMap;

@WillRefactor(aspect = "name")
public enum TropicalFishPattern {
    KOB("kob", TropicalFish.Base.SMALL, 0),
    SUNSTREAK("sunstreak", TropicalFish.Base.SMALL, 1),
    SNOOPER("snooper", TropicalFish.Base.SMALL, 2),
    DASHER("dasher", TropicalFish.Base.SMALL, 3),
    BRINELY("brinely", TropicalFish.Base.SMALL, 4),
    SPOTTY("spotty", TropicalFish.Base.SMALL, 5),
    FLOPPER("flopper", TropicalFish.Base.LARGE, 0),
    STRIPEY("stripey", TropicalFish.Base.LARGE, 1),
    GLITTER("glitter", TropicalFish.Base.LARGE, 2),
    BLOCKFISH("blockfish", TropicalFish.Base.LARGE, 3),
    BETTY("betty", TropicalFish.Base.LARGE, 4),
    CLAYFISH("clayfish", TropicalFish.Base.LARGE, 5);

    public final int index;
    public final String name;

    TropicalFishPattern(String name, TropicalFish.Base base, int subID) {
        this.index = ((ITropicalFish$Base) (Object) base).avoid$id() | subID << 8;
        this.name = name;
    }

    public static final HashMap<Integer, TropicalFishPattern> BY_ID;
    static {
        BY_ID = new HashMap<>();

        var members = values();
        for (TropicalFishPattern member : members)
            BY_ID.put(member.index, member);
    }

    private static final HashMap<TropicalFishPattern, TropicalFish.Pattern> REVERSE;
    static {
        REVERSE = new HashMap<>();

        for (TropicalFish.Pattern member : TropicalFish.Pattern.values())
            REVERSE.put(BY_ID.get(member.getPackedId()), member);
    }

    public static final class Controller
           implements TransformingItemComponentValue<TropicalFish.Pattern, TropicalFishPattern>
    {
        @Override
        public TropicalFishPattern transform(TropicalFish.Pattern value) {
            return TropicalFishPattern.BY_ID.get(value.getPackedId());
        }

        @Override
        public TropicalFish.Pattern untransform(TropicalFishPattern value) {
            return REVERSE.get(value);
        }
    }
}
