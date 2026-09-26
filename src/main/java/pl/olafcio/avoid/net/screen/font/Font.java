package pl.olafcio.avoid.net.screen.font;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

@ApiStatus.NonExtendable
public abstract class Font {
    public abstract int width(String text);
    public abstract int width(BaseComponent<?> component);
    public abstract int height();
    public abstract int wrappedHeight(BaseComponent<?> component, int maxWidth);
}
