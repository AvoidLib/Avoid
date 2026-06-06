package pl.olafcio.avoid.net.chat.component;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.annotations.Untested;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

import java.awt.*;

@NullMarked
@NeverRemoval
@ApiStatus.Experimental
@SuppressWarnings("NullableProblems")
public enum Colors {
    BLACK("black", '0', 0, new Color(0)),
    DARK_BLUE("dark_blue", '1', 1, new Color(0xaa)),
    DARK_GREEN("dark_green", '2', 2, new Color(0xaa00)),
    DARK_AQUA("dark_aqua", '3', 3, new Color(0xaaaa)),
    DARK_RED("dark_red", '4', 4, new Color(0xaa0000)),
    DARK_PURPLE("dark_purple", '5', 5, new Color(0xaa00aa)),
    GOLD("gold", '6', 6, new Color(0xffaa00)),
    GRAY("gray", '7', 7, new Color(0xaaaaaa)),
    DARK_GRAY("dark_gray", '8', 8, new Color(0x555555)),
    BLUE("blue", '9', 9, new Color(0x5555ff)),
    GREEN("green", 'a', 10, new Color(0x55ff55)),
    AQUA("aqua", 'b', 11, new Color(0x55ffff)),
    RED("red", 'c', 12, new Color(0xff5555)),
    @Untested(specifically = "MiniMessage tag might not be correct")
    LIGHT_PURPLE("light_purple", 'd', 13, new Color(0xff55ff)),
    YELLOW("yellow", 'e', 14, new Color(0xffff55)),
    WHITE("white", 'f', 15, new Color(0xffffff)),

    OBFUSCATED("obfuscated", 'k'),
    BOLD("b", 'l'),
    STRIKETHROUGH("st", 'm'),
    UNDERLINE("u", 'n'),
    ITALIC("i", 'o'),
    RESET("reset", 'r', -1);

    final String minimessage_tag;
    final char code;
    @Nullable final Integer index;
    @Nullable final Color color;
    @Nullable final Integer rgb;
    final boolean isFormatting;
    final boolean isReset;

    Colors(String minimessage_tag, char code, int index, Color color) {
        this.minimessage_tag = minimessage_tag;
        this.code = code;
        this.index = index;
        this.color = color;
        this.rgb = color.getRGB();
        this.isFormatting = false;
        this.isReset = false;
    }

    Colors(String minimessage_tag, char code) {
        this.minimessage_tag = minimessage_tag;
        this.code = code;
        this.index = null;
        this.color = null;
        this.rgb = null;
        this.isFormatting = true;
        this.isReset = false;
    }

    Colors(String minimessage_tag, char code, int index) {
        this.minimessage_tag = minimessage_tag;
        this.code = code;
        this.index = index;
        this.color = null;
        this.rgb = null;
        this.isFormatting = true;
        this.isReset = true;
    }

    // ///////// //
    //  GETTERS  //
    // ///////// //

    public String getMinimessageTag() {
        return "<" + minimessage_tag + ">";
    }

    public char getCode() {
        return code;
    }

    @UnknownNullability
    public Integer getIndex() {
        return index;
    }

    @UnknownNullability
    public Color getColor() {
        return color;
    }

    @UnknownNullability
    public Integer getRGB() {
        return rgb;
    }

    // ////////////// //
    //  CONDITIONALS  //
    // ////////////// //

    public boolean isColor() {
        return !isFormatting;
    }

    public boolean isFormatting() {
        return isFormatting && !isReset;
    }

    public boolean isReset() {
        return isReset;
    }

    public boolean isFormattingOrReset() {
        return isFormatting;
    }
}
