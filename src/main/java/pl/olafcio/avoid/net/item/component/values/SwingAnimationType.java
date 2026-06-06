package pl.olafcio.avoid.net.item.component.values;

import java.util.Arrays;
import java.util.HashMap;

public enum SwingAnimationType {
    NONE(0, "none"),
    WHACK(1, "whack"),
    STAB(2, "stab");

    private final int index;
    private final String name;

    SwingAnimationType(final int index, final String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return this.index;
    }

    public String getSerializedName() {
        return this.name;
    }

    static final HashMap<SwingAnimationType, net.minecraft.world.item.SwingAnimationType> LOOKUP
           = new HashMap<>();

    static final HashMap<net.minecraft.world.item.SwingAnimationType, SwingAnimationType> REV_LOOKUP
           = new HashMap<>();

    static {
        Arrays.stream(SwingAnimationType.values()).forEach(type -> {
            var mctype = net.minecraft.world.item.SwingAnimationType.valueOf(type.name);

            LOOKUP.put(type, mctype);
            REV_LOOKUP.put(mctype, type);
        });
    }
}
