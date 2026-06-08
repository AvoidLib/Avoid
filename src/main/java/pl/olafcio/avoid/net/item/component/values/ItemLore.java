package pl.olafcio.avoid.net.item.component.values;

import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.component.ChatStyle;
import pl.olafcio.avoid.net.chat.component.Colors;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.List;

@NullMarked
public record ItemLore(List<? extends BaseComponent<?>> lines, List<? extends BaseComponent<?>> styledLines) {
    public static final ChatStyle LORE_STYLE
                  = new ChatStyle().fun().withColor(Colors.DARK_PURPLE)
                                         .withItalic(true)
                                   .back();

    public ItemLore(List<? extends BaseComponent<?>> list) {
        this(list, list.stream().map(ChatStyle.merging(LORE_STYLE)).toList());
    }

    public ItemLore {
        if (lines.size() > 256) {
            throw new IllegalArgumentException("Got " + lines.size() + " lines, but maximum is 256");
        }
    }

    public static final class Controller
           implements TransformingItemComponentValue<net.minecraft.world.item.component.ItemLore, ItemLore>
    {
        @Override
        public ItemLore transform(net.minecraft.world.item.component.ItemLore value) {
            return new ItemLore(value.lines().stream().map(COFromNative::from).toList(), value.styledLines().stream().map(COFromNative::from).toList());
        }

        @Override
        public net.minecraft.world.item.component.ItemLore untransform(ItemLore value) {
            return new net.minecraft.world.item.component.ItemLore(value.lines.stream().map(COToNative::from).toList(), value.styledLines.stream().map(COToNative::from).toList());
        }
    }
}
