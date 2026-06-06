package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.world.level.material.MapColor;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;
import pl.olafcio.avoid.net.util.Coloring;

import java.awt.*;
import java.util.HashMap;

// TODO: Wrap MapColor

@WillRefactor(aspect = "name")
public enum DyeColor {
    WHITE(0, "white", new Color(0xf9fffe), MapColor.SNOW, new Color(0xf0f0f0), new Color(0xffffff)),
    ORANGE(1, "orange", new Color(0xf9801d), MapColor.COLOR_ORANGE, new Color(0xeb8844), new Color(0xff681f)),
    MAGENTA(2, "magenta", new Color(0xc74ebd), MapColor.COLOR_MAGENTA, new Color(0xc354cd), new Color(0xff00ff)),
    LIGHT_BLUE(3, "light_blue", new Color(0x3ab3da), MapColor.COLOR_LIGHT_BLUE, new Color(0x6689d3), new Color(0x9ac0cd)),
    YELLOW(4, "yellow", new Color(0xfed83d), MapColor.COLOR_YELLOW, new Color(0xdecf2a), new Color(0xffff00)),
    LIME(5, "lime", new Color(0x80c71f), MapColor.COLOR_LIGHT_GREEN, new Color(0x41cd34), new Color(0xbfff00)),
    PINK(6, "pink", new Color(0xf38baa), MapColor.COLOR_PINK, new Color(0xd88198), new Color(0xff69b4)),
    GRAY(7, "gray", new Color(0x474f52), MapColor.COLOR_GRAY, new Color(0x434343), new Color(0x808080)),
    LIGHT_GRAY(8, "light_gray", new Color(0x9d9d97), MapColor.COLOR_LIGHT_GRAY, new Color(0xababab), new Color(0xd3d3d3)),
    CYAN(9, "cyan", new Color(0x169c9c), MapColor.COLOR_CYAN, new Color(0x287697), new Color(0xffff)),
    PURPLE(10, "purple", new Color(0x8932b8), MapColor.COLOR_PURPLE, new Color(0x7b2fbe), new Color(0xa020f0)),
    BLUE(11, "blue", new Color(0x3c44aa), MapColor.COLOR_BLUE, new Color(0x253192), new Color(0xff)),
    BROWN(12, "brown", new Color(0x835432), MapColor.COLOR_BROWN, new Color(0x51301a), new Color(0x8b4513)),
    GREEN(13, "green", new Color(0x5e7c16), MapColor.COLOR_GREEN, new Color(0x3b511a), new Color(0xff00)),
    RED(14, "red", new Color(0xb02e26), MapColor.COLOR_RED, new Color(0xb3312c), new Color(0xff0000)),
    BLACK(15, "black", new Color(0x1d1d21), MapColor.COLOR_BLACK, new Color(0x1e1b1b), new Color(0x0));

    public final int index;
    public final String colorName;
    public final Color textureDiffuseColor;
    public final int textureDiffuseColorCode;
    public final MapColor mapColor;
    public final Color fireworkColor;
    public final Color textColor;
    public final int textColorCode;

    DyeColor(int index, String colorName, Color textureDiffuseColor, MapColor mapColor, Color fireworkColor, Color textColor) {
        this.index = index;
        this.colorName = colorName;
        this.textureDiffuseColor = textureDiffuseColor;
        this.textureDiffuseColorCode = Coloring.toARGB(textureDiffuseColor);
        this.mapColor = mapColor;
        this.fireworkColor = fireworkColor;
        this.textColor = textColor;
        this.textColorCode = Coloring.toARGB(textColor);
    }

    public static final HashMap<Integer, DyeColor> BY_ID;
    static {
        BY_ID = new HashMap<>();

        var members = values();
        for (DyeColor member : members)
            BY_ID.put(member.index, member);
    }

    private static final HashMap<DyeColor, net.minecraft.world.item.DyeColor> REVERSE;
    static {
        REVERSE = new HashMap<>();

        for (net.minecraft.world.item.DyeColor member : net.minecraft.world.item.DyeColor.values())
            REVERSE.put(BY_ID.get(member.getId()), member);
    }

    public static final class Controller
           implements TransformingItemComponentValue<net.minecraft.world.item.DyeColor, DyeColor>
    {
        @Override
        public DyeColor transform(net.minecraft.world.item.DyeColor value) {
            return DyeColor.BY_ID.get(value.getId());
        }

        @Override
        public net.minecraft.world.item.DyeColor untransform(DyeColor value) {
            return REVERSE.get(value);
        }
    }
}
