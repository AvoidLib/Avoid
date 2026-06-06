package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.chat.component.Colors;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public enum Rarity {
    COMMON(0, "common", Colors.WHITE),
    UNCOMMON(1, "uncommon", Colors.YELLOW),
    RARE(2, "rare", Colors.AQUA),
    EPIC(3, "epic", Colors.LIGHT_PURPLE);

    private final int index;
    private final String name;
    private final Colors style;

    Rarity(int index, String name, Colors style) {
        this.index = index;
        this.name = name;
        this.style = style;
    }

    public int getIndex() {
        return index;
    }
    public Colors getColor() {
        return this.style;
    }
    public String getCodeName() {
        return this.name;
    }

    public static final class Controller
           implements TransformingItemComponentValue<net.minecraft.world.item.Rarity, Rarity>
    {
        @Override
        public Rarity transform(net.minecraft.world.item.Rarity value) {
            return Rarity.values()[value.ordinal()];
        }

        @Override
        public net.minecraft.world.item.Rarity untransform(Rarity value) {
            return net.minecraft.world.item.Rarity.values()[value.ordinal()];
        }
    }
}
