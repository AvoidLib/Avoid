package pl.olafcio.avoid_impl.net.screen;

import pl.olafcio.avoid.net.screen.Screen;
import pl.olafcio.avoid.net.screen.ScreenMarker;

import java.util.function.Supplier;

public enum ScreensNative {
    ;

    public static void overwrite(ScreenMarker oldScreen, Screen newScreen) {
        // no-op
    }

    public static void overwrite(ScreenMarker oldScreen, Supplier<Screen> newScreen) {
        // no-op
    }

    public static void overwrite(ScreenMarker oldScreen, Class<? extends Screen> newScreen) {
        // no-op
    }

    public static void open(Screen newScreen) {
        // no-op
    }

    public static void open(ScreenMarker newScreen) {
        // no-op
    }

    public static void close() {
        // no-op
    }
}
