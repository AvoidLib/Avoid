package pl.olafcio.avoid.net.screen;

import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.options.LanguageSelectScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import static pl.olafcio.avoid.net.screen.ScreenMarker.*;

import java.util.HashMap;

@Native
@ApiStatus.Internal
public enum ScreenMarkerNative {
    ;

    static final HashMap<ScreenMarker, Class<?>> LOOKUP
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
}
