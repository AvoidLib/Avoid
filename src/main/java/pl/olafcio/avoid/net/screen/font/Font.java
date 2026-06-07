package pl.olafcio.avoid.net.screen.font;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COToNative;

public final class Font {
    net.minecraft.client.gui.Font font;

    @ApiStatus.Internal
    Font(net.minecraft.client.gui.Font font) {
        this.font = font;
    }

    public int width(String text) {
        return font.width(text);
    }

    public int width(BaseComponent<?> component) {
        return font.width(COToNative.from(component));
    }

    public int height() {
        return font.lineHeight;
    }

    public int wrappedHeight(BaseComponent<?> component, int maxWidth) {
        return font.wordWrapHeight(COToNative.from(component), maxWidth);
    }
}
