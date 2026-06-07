package pl.olafcio.avoid.net.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.options.LanguageSelectScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

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

    static final HashMap<ScreenMarker, Class<? extends Screen>> LOOKUP
           = new HashMap<>();

    static {
        LOOKUP.put(TITLESCREEN, TitleScreen.class);
        LOOKUP.put(SINGLEPLAYER_SELECTION, SelectWorldScreen.class);
        LOOKUP.put(MULTIPLAYER_SELECTION, JoinMultiplayerScreen.class);
        LOOKUP.put(MULTIPLAYER_NOT_MONITORED, SafetyScreen.class);
        LOOKUP.put(CREATE_WORLD, CreateWorldScreen.class);
        LOOKUP.put(DIRECT_CONNECT, DirectJoinServerScreen.class);
        LOOKUP.put(MANAGE_SERVER, ManageServerScreen.class);
        LOOKUP.put(CONNECT, ConnectScreen.class);
        LOOKUP.put(CONFIRM, ConfirmScreen.class);
        LOOKUP.put(OPTIONS, OptionsScreen.class);
        LOOKUP.put(ACCESSIBILITY_OPTIONS, AccessibilityOptionsScreen.class);
        LOOKUP.put(LANGUAGE_SELECT, LanguageSelectScreen.class);
    }

    boolean is(Screen screen) {
        return LOOKUP.get(this).isInstance(screen);
    }

    Class<? extends Screen> get() {
        return LOOKUP.get(this);
    }

    public final pl.olafcio.avoid.net.screen.Screen create() {
        var screen = LOOKUP.get(this);

        try {
            var constructors = screen.getDeclaredConstructors();

            for (var con : constructors) {
                if (con.getParameterCount() == 0) {
                    con.setAccessible(true);

                    return new NativeScreen((Screen) con.newInstance());
                } else if (con.getParameterCount() == 1 && Screen.class.isAssignableFrom(con.getParameters()[0].getType())) {
                    con.setAccessible(true);

                    return new NativeScreen((Screen) con.newInstance(Avoid.mc.screen));
                }
            }

            throw new RuntimeException("[ScreenMarker#create] Unable to find constructor for '%s (%s)'".formatted(this, screen));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("[ScreenMarker#create] Failed to construct screen '%s (%s)'".formatted(this, screen), e);
        }
    }
}
