package pl.olafcio.avoid.net.screen;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.mixininterface.IMinecraft;
import pl.olafcio.avoid.mixininterface.IScreen;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

@Native
@ApiStatus.Internal
public final class ScreensNative {
    @ApiStatus.Internal
    private ScreensNative() {}

    public static void overwrite(ScreenMarker oldScreen, Supplier<Screen> newScreen) {
        ((IMinecraft) Avoid.mc).avoidlib$overwrites().put((Class<? extends net.minecraft.client.gui.screens.Screen>) oldScreen.get(), () -> new AvoidScreen(newScreen.get()));

        if (oldScreen.is((IScreen) Avoid.mc.screen))
            Avoid.mc.setScreen(new AvoidScreen(newScreen.get()));
    }

    public static void overwrite(ScreenMarker oldScreen, Screen newScreen) {
        ((IMinecraft) Avoid.mc).avoidlib$overwrites().put((Class<? extends net.minecraft.client.gui.screens.Screen>) oldScreen.get(), () -> new AvoidScreen(newScreen));

        if (oldScreen.is((IScreen) Avoid.mc.screen))
            Avoid.mc.setScreen(new AvoidScreen(newScreen));
    }

    public static void overwrite(ScreenMarker oldScreen, Class<? extends Screen> newScreen) {
        Constructor<? extends Screen> constructor;

        try {
            constructor = newScreen.getDeclaredConstructor();
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("[Screens#overwrite] Failed to access newScreen constructor (to fix, use the constructor accessor; e.g.: Screens.overwrite(..., NewTitleScreen::new))", e);
        }

        Supplier<net.minecraft.client.gui.screens.Screen> make = () -> {
            try {
                return new AvoidScreen(constructor.newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("[Screens#overwrite] Failed to construct newScreen", e);
            }
        };

        ((IMinecraft) Avoid.mc).avoidlib$overwrites().put((Class<? extends net.minecraft.client.gui.screens.Screen>) oldScreen.get(), make);

        if (oldScreen.is((IScreen) Avoid.mc.screen))
            Avoid.mc.setScreen(make.get());
    }

    public static void open(Screen screen) {
        Avoid.mc.setScreen(new AvoidScreen(screen));
    }

    public static void open(ScreenMarker screen) {
        Avoid.mc.setScreen(new AvoidScreen(screen.create()));
    }

    public static void openWithoutHandler(NativeScreen screen) {
        Avoid.mc.setScreen(screen.realScreen);
    }

    public static void close() {
        Avoid.mc.setScreen(null);
    }
}
