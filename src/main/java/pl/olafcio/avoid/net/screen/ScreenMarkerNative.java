package pl.olafcio.avoid.net.screen;

import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.dialog.ServerLinksDialogScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.options.*;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
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
        LOOKUP.put(SKIN_CUSTOMIZATION, SkinCustomizationScreen.class);
        LOOKUP.put(MUSIC_AND_SOUNDS, SoundOptionsScreen.class);
        LOOKUP.put(VIDEO_SETTINGS, VideoSettingsScreen.class);
        LOOKUP.put(CONTROLS_SCREEN, ControlsScreen.class);
        LOOKUP.put(MOUSE_SETTINGS_SCREEN, MouseSettingsScreen.class);
        LOOKUP.put(KEYBINDS_SCREEN, KeyBindsScreen.class);
        LOOKUP.put(CHAT_SETTINGS, ChatOptionsScreen.class);
        LOOKUP.put(CREDITS_AND_ATTRIBUTION, CreditsAndAttributionScreen.class);
        LOOKUP.put(ENDGAME, WinScreen.class);
        LOOKUP.put(CHAT, ChatScreen.class);
        LOOKUP.put(CREATIVE_INVENTORY, CreativeModeInventoryScreen.class);
        LOOKUP.put(OPEN_TO_LAN, ShareToLanScreen.class);
        LOOKUP.put(ADVANCEMENTS, AdvancementsScreen.class);
        LOOKUP.put(PACK_SELECTION, PackSelectionScreen.class);
        LOOKUP.put(SERVER_LINKS, ServerLinksDialogScreen.class);
    }
}
