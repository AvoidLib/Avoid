package pl.olafcio.avoid.net.screen_modifier;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.screen.Screen;

@Native
@ApiStatus.Internal
public final class ScreenModifierNative {
    @ApiStatus.Internal
    private ScreenModifierNative() {}

    public static void setScreen(ScreenModifier modifier, Screen screen) {
        modifier.screen = screen;
    }

    public static void setCancelled(ScreenModifier modifier, boolean value) {
        modifier.cancel = value;
    }

    public static void setSkip(ScreenModifier modifier, boolean value) {
        modifier.skip = value;
    }

    public static void setFunction(ScreenModifier modifier, String name, Function function) {
        modifier.functionName = name;
        modifier.function     = function;
    }

    public static void unsetFunction(ScreenModifier modifier) {
        modifier.functionName = null;
        modifier.function     = null;
    }

    public static boolean isCancelled(ScreenModifier modifier) {
        var val = modifier.cancel;

        modifier.cancel = true;

        return val;
    }
}
