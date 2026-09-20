package pl.olafcio.avoid.net.chat.component._parse;

import org.apache.commons.lang3.CharUtils;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.*;
import pl.olafcio.avoid.net.chat.component.type.ParentComponent;

import java.awt.*;

/**
 * @apiNote Use {@link Components.Parse#bukkit Components.Parse.bukkit()} instead!
 */
@ApiStatus.Internal
public class BukkitParser {
    protected final int[] text;
    protected int index;
    protected BaseComponent<?> output;

    public BukkitParser(String text) {
        this.text = text.codePoints().toArray();
        this.index = 0;
        this.output = ParentComponent.of();
    }

    public BaseComponent<?> parse() {
        var value = new StringBuilder();
        var hex   = new StringBuilder();

        var inTag = false;
        var inHex = false;

        var style = new ChatStyle();

        for (var ch : text) {
            if (inHex) {
                if (ch > 255 || !CharUtils.isHex((char) ch)) {
                    inHex = false;

                    value.append("&#");
                    value.append(hex);

                    hex.setLength(0);
                } else {
                    hex.append(ch);

                    if (hex.length() == 6) {
                        inHex = false;
                        style.color = new Color(Integer.parseInt(hex.toString(), 16));
                    }
                }
            } else if (inTag) {
                if (ch == '#') {
                    inHex = true;
                } else if (ch == 'l') {
                    style.bold = true;
                } else if (ch == 'o') {
                    style.italic = true;
                } else if (ch == 'k') {
                    style.obfuscated = true;
                } else if (ch == 'n') {
                    style.underlined = true;
                } else if (ch == 'm') {
                    style.strikethrough = true;
                } else if (ch == 'r') {
                    style.bold = false;
                    style.italic = false;
                    style.obfuscated = false;
                    style.underlined = false;
                    style.strikethrough = false;
                } else {
                    process:
                    {
                        var colors = Colors.values();
                        for (var col : colors) {
                            if (col.isColor() && col.getCode() == ch) {
                                style.color = col.getColor();
                                break process;
                            }
                        }

                        value.appendCodePoint('&');
                        value.appendCodePoint(ch);
                    }
                }

                inTag = false;
            } else if (ch == '&') {
                if (!value.isEmpty()) {
                    var comp = Components.literal(value.toString());

                    comp.styleAs(style.clone());

                    output.append(comp);
                    value.setLength(0);
                }

                inTag = true;
            } else {
                value.appendCodePoint(ch);
            }
        }

        if (!value.isEmpty()) {
            var comp = Components.literal(value.toString());

            comp.styleAs(style.clone());

            output.append(comp);
            value.setLength(0);
        }

        return output;
    }
}
