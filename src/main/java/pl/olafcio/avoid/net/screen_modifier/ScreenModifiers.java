package pl.olafcio.avoid.net.screen_modifier;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.net.screen.ScreenMarker;
import pl.olafcio.avoid.net.screen.ScreenMarkerNative;

import java.util.function.Supplier;

@ApiStatus.Experimental
public final class ScreenModifiers {
    @ApiStatus.Internal
    private ScreenModifiers() {}

    public static void register(Supplier<ScreenModifier> supplier, ScreenMarker screenMarker) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT)
            ScreenModifiersNative.register(supplier, ScreenMarkerNative.LOOKUP.get(screenMarker));
    }
}
