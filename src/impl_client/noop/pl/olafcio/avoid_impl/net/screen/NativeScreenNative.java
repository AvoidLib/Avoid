package pl.olafcio.avoid_impl.net.screen;

import pl.olafcio.avoid.net.screen.Screen;
import pl.olafcio.avoid_impl.mixininterface.IScreen;

public enum NativeScreenNative {
    ;

    public static Screen create(IScreen screen) {
        throw new UnsupportedOperationException();
    }
}
