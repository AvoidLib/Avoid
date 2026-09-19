package pl.olafcio.avoid.net.chat.component._parse;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.*;
import pl.olafcio.avoid.net.chat.component.type.ParentComponent;

import java.awt.*;

/**
 * @apiNote Use {@link pl.olafcio.avoid.net.chat.component.Components.Parse#minimessage Components.Parse.minimessage()} instead!
 */
@ApiStatus.Internal
public class MiniMessageParser {
    protected final int[] text;
    protected int index;
    protected BaseComponent<?> output;

    public MiniMessageParser(String text) {
        this.text = text.codePoints().toArray();
        this.index = 0;
        this.output = ParentComponent.of();
    }

    public BaseComponent<?> parse() {
        var value = new StringBuilder();
        var tag   = new StringBuilder();

        var inTag = false;
        var style = new ChatStyle();

        for (var ch : text) {
            if (inTag) {
                if (ch == '>') {
                    inTag = false;

                    //TODO hover, click, objects, translations, keymap, gradient, prides, rainbow, transition

                    var tagstr = tag.toString().toLowerCase();
                    if (tagstr.startsWith("#")) {
                        style.color = new Color(Integer.parseInt(tagstr.substring(1), 16));
                    } else {
                        var state = true;

                        if (tagstr.startsWith("/")) {
                            state = false;
                            tagstr = tagstr.substring(1);
                        } else {
                            try {
                                var color = Colors.valueOf(tagstr.toUpperCase());
                                if (color.isColor()) {
                                    style.color = color.getColor();
                                    continue;
                                }
                            } catch (Exception ignored) {
                            }
                        }

                        switch (tagstr) {
                            case "b",  "bold" -> style.bold = state;
                            case "i",  "italic" -> style.italic = state;
                            case "u",  "underlined" -> style.underlined = state;
                            case "st", "strikethrough" -> style.strikethrough = state;
                            case "o",  "obfuscated" -> style.obfuscated = state;
                            default -> throw new ParsingException("Invalid MiniMessage tag: '<%s>'".formatted(tagstr));
                        }
                    }

                    tag.setLength(0);
                } else {
                    tag.appendCodePoint(ch);
                }
            } else if (ch == '<') {
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
