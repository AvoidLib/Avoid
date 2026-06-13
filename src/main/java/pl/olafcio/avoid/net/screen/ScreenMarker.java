package pl.olafcio.avoid.net.screen;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.client.AvoidLibClient;
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
    /** The screen that shows when Minecraft wants you to confirm a decision. */ CONFIRM,
    /** The settings screen. */ OPTIONS,
    /** The accessibility settings screen. */ ACCESSIBILITY_OPTIONS,
    /** The language selection screen. */ LANGUAGE_SELECT,
    /** The skin customization screen. */ SKIN_CUSTOMIZATION,
    /** The music & sound options screen. */ MUSIC_AND_SOUNDS,
    /** The video settings screen. */ VIDEO_SETTINGS,
    /** The general controls settings screen. */ CONTROLS_SCREEN,
    /** The {@code Controls} -> {@code Mouse Settings} screen. */ MOUSE_SETTINGS_SCREEN,
    /** The {@code Controls} -> {@code Key Binds} screen. */ KEYBINDS_SCREEN,
    /** The chat settings screen. */ CHAT_SETTINGS,
    /** The general "Credits and Attribution" settings screen. */ CREDITS_AND_ATTRIBUTION,
    /** The credits screen showing after you finish the end fight. */ ENDGAME,
    /** The chat screen you can enter by clicking {@code T} ingame. */ CHAT,
    /** The inventory screen that shows when you click {@code E} while in creative mode. */ CREATIVE_INVENTORY,
    /** The screen that shows when you click "Open to LAN" in the pause menu while in a singleplayer world. */ OPEN_TO_LAN,
    /** The screen that shows when you click "Advancements" in the pause menu. */ ADVANCEMENTS,
    /** The resource-pack or data-pack selection screen. */ PACK_SELECTION,
    /** The {@code Server Links} settings screen, only visible on servers that have it on. */ SERVER_LINKS;

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

                    return new NativeScreen((IScreen) con.newInstance(AvoidLibClient.mc.screen));
                }
            }

            throw new RuntimeException("[ScreenMarker#create] Unable to find constructor for '%s (%s)'".formatted(this, screen));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("[ScreenMarker#create] Failed to construct screen '%s (%s)'".formatted(this, screen), e);
        }
    }
}
