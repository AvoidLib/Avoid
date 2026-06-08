package pl.olafcio.avoid.net.chat.component;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.event.Click;
import pl.olafcio.avoid.net.chat.component.event.Hover;
import pl.olafcio.avoid.net.id.Identification;

import java.awt.*;
import java.util.function.Function;

@ApiStatus.Experimental
public final class ChatStyle {
    public Color color = null;
    public Color shadow = null;

    public Boolean bold = null;
    public Boolean italic = null;
    public Boolean underlined = null;
    public Boolean strikethrough = null;
    public Boolean obfuscated = null;

    public Identification font = null;

    public Click click = null;
    public Hover hover = null;

    // ///////////////// //
    //  FUNCTIONAL WITH  //
    // ///////////////// //

    @ApiStatus.Experimental
    public fun fun() {
        return new fun(this);
    }

    @ApiStatus.Experimental
    public final class fun {
        private final ChatStyle back;

        @ApiStatus.Internal
        private fun(ChatStyle back) {
            this.back = back;
        }

        public ChatStyle back() {
            return back;
        }

        public fun withColor(Color color) {
            ChatStyle.this.color = color;
            return this;
        }

        public fun withColor(Colors color) {
            ChatStyle.this.color = color.getColor();
            return this;
        }

        public fun withShadow(Color shadow) {
            ChatStyle.this.shadow = shadow;
            return this;
        }

        public fun withShadow(Colors shadow) {
            ChatStyle.this.shadow = shadow.getColor();
            return this;
        }

        public fun withBold(Boolean bold) {
            ChatStyle.this.bold = bold;
            return this;
        }

        public fun withItalic(Boolean italic) {
            ChatStyle.this.italic = italic;
            return this;
        }

        public fun withUnderlined(Boolean underlined) {
            ChatStyle.this.underlined = underlined;
            return this;
        }

        public fun withStrikethrough(Boolean strikethrough) {
            ChatStyle.this.strikethrough = strikethrough;
            return this;
        }

        public fun withObfuscated(Boolean obfuscated) {
            ChatStyle.this.obfuscated = obfuscated;
            return this;
        }

        public fun withFont(Identification font) {
            ChatStyle.this.font = font;
            return this;
        }

        public fun withClick(Click click) {
            ChatStyle.this.click = click;
            return this;
        }

        public fun withHover(Hover hover) {
            ChatStyle.this.hover = hover;
            return this;
        }
    }

    // ////////////////// //
    //  FUNCTIONAL MERGE  //
    // ////////////////// //

    @ApiStatus.Experimental
    public merge merge() {
        return new merge(this);
    }

    @ApiStatus.Experimental
    public final class merge {
        private final ChatStyle back;

        @ApiStatus.Internal
        private merge(ChatStyle back) {
            this.back = back;
        }

        public ChatStyle back() {
            return back;
        }

        public merge clear() {
            ChatStyle.this.color = null;
            ChatStyle.this.shadow = null;

            ChatStyle.this.bold = null;
            ChatStyle.this.italic = null;
            ChatStyle.this.underlined = null;
            ChatStyle.this.strikethrough = null;
            ChatStyle.this.obfuscated = null;

            ChatStyle.this.font = null;

            ChatStyle.this.click = null;
            ChatStyle.this.hover = null;

            return this;
        }

        public merge override(ChatStyle from) {
            if (from.color != null) ChatStyle.this.color = from.color;
            if (from.shadow != null) ChatStyle.this.shadow = from.shadow;

            if (from.bold != null) ChatStyle.this.bold = from.bold;
            if (from.italic != null) ChatStyle.this.italic = from.italic;
            if (from.underlined != null) ChatStyle.this.underlined = from.underlined;
            if (from.strikethrough != null) ChatStyle.this.strikethrough = from.strikethrough;
            if (from.obfuscated != null) ChatStyle.this.obfuscated = from.obfuscated;

            if (from.font != null) ChatStyle.this.font = from.font;

            if (from.click != null) ChatStyle.this.click = from.click;
            if (from.hover != null) ChatStyle.this.hover = from.hover;

            return this;
        }
    }

    public static Function<BaseComponent<?>, BaseComponent<?>> merging(ChatStyle from) {
        return cmp -> cmp.style(st -> st.merge().override(from));
    }
}
