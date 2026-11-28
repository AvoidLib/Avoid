package pl.olafcio.avoid_impl.net.screen;

import net.minecraft.client.gui.screens.Screen;
import pl.olafcio.avoid.net.screen.ScreenMarker;

import java.util.Map;

public enum ScreenMarkerNative {
    ;

    public static final Map<ScreenMarker, Class<? extends Screen>> LOOKUP = Map.of();

    public static pl.olafcio.avoid.net.screen.Screen create(ScreenMarker screenMarker) {
        throw new UnsupportedOperationException();
    }
}
