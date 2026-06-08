package pl.olafcio.avoid.net.screen;

import net.minecraft.client.gui.screens.*;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.mixininterface.IScreen;

import java.lang.reflect.InvocationTargetException;

@ApiStatus.Experimental
public enum ScreenMarker {
    /** The game title screen.                   */ TITLESCREEN,
    /** The singleplayer world selection screen. */ SINGLEPLAYER_SELECTION,
    /** The multiplayer server selection screen. */ MULTIPLAYER_SELECTION,
    /** The multiplayer "3rd-party servers are not monitored by Microsoft" screen. */ MULTIPLAYER_NOT_MONITORED,
    /** The singleplayer "Create World" screen.  */ CREATE_WORLD,
    /** The multiplayer "Direct Connect" screen. */ DIRECT_CONNECT,
    /** The multiplayer "Add Server" and "Edit Server" screen. */ MANAGE_SERVER,
    /** The screen that shows when you are connecting to a server. */ CONNECT,
    /** The screen that shows when Minecrarft wants you to confirm a decision. */ CONFIRM,
    /** The settings screen. */ OPTIONS,
    /** The accessibility settings screen. */ ACCESSIBILITY_OPTIONS,
    /** The language selection screen. */ LANGUAGE_SELECT;

    boolean is(IScreen screen) {
        return ScreenMarkerNative.LOOKUP.get(this).isInstance(screen);
    }

    @SuppressWarnings("unchecked")
    Class<? extends IScreen> get() {
        return (Class<? extends IScreen>) ScreenMarkerNative.LOOKUP.get(this);
    }

    public final pl.olafcio.avoid.net.screen.Screen create() {
        var screen = ScreenMarkerNative.LOOKUP.get(this);

        try {
            var constructors = screen.getDeclaredConstructors();

            for (var con : constructors) {
                if (con.getParameterCount() == 0) {
                    con.setAccessible(true);

                    return new NativeScreen((IScreen) con.newInstance());
                } else if (con.getParameterCount() == 1 && IScreen.class.isAssignableFrom(con.getParameters()[0].getType())) {
                    con.setAccessible(true);

                    return new NativeScreen((IScreen) con.newInstance(Avoid.mc.screen));
                }
            }

            throw new RuntimeException("[ScreenMarker#create] Unable to find constructor for '%s (%s)'".formatted(this, screen));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("[ScreenMarker#create] Failed to construct screen '%s (%s)'".formatted(this, screen), e);
        }
    }
}
