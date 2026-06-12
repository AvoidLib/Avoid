package pl.olafcio.avoid.mixinclass;

import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.component.Components;
import pl.olafcio.avoid.net.chat.component.type.TextComponent;
import pl.olafcio.avoid.net.chat.converter.COToNative;

import java.util.List;

public class MyFormattedCharSequence implements FormattedCharSequence {
    private final List<BaseComponent<?>> array;

    public MyFormattedCharSequence(List<BaseComponent<?>> array) {
        this.array = array;
    }

    @Override
    public boolean accept(FormattedCharSink formattedCharSink) {
        for (int index = 0; index < array.size(); index++) {
            var el = (TextComponent) array.get(index);

            formattedCharSink.accept(index, COToNative.from(Components.literal("")
                                                      .styleAs(el.style())).getStyle(), el.text().charAt(0));
        }

        return true;
    }
}
