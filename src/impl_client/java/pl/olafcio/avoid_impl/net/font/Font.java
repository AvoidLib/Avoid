package pl.olafcio.avoid_impl.net.font;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid_impl.net.chat.converter.COToNative;

public final class Font extends pl.olafcio.avoid.net.screen.font.Font {
    net.minecraft.client.gui.Font font;

    @ApiStatus.Internal
    Font(net.minecraft.client.gui.Font font) {
        this.font = font;
    }

    @Override
    public int width(String text) {
        return font.width(text);
    }

    @Override
    public int width(BaseComponent<?> component) {
        return font.width(COToNative.from(component));
    }

    @Override
    public int height() {
        return font.lineHeight;
    }

    @Override
    public int wrappedHeight(BaseComponent<?> component, int maxWidth) {
        return font.wordWrapHeight(COToNative.from(component), maxWidth);
    }
}
