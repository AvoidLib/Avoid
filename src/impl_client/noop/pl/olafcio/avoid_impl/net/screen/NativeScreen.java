package pl.olafcio.avoid_impl.net.screen;

import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.screen.Screen;

public abstract class NativeScreen extends AvoidScreen {
    public NativeScreen(BaseComponent<?> accessibilityTitle) {
        super(a());
    }

    private static Screen a() {
        throw new UnsupportedOperationException();
    }
}
