package pl.olafcio.avoid.net.screen;

import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.debug.DebugOptionsScreen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.gui.screens.dialog.ServerLinksDialogScreen;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.client.gui.screens.multiplayer.CodeOfConductScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.multiplayer.ServerReconfigScreen;
import net.minecraft.client.gui.screens.options.*;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.client.gui.screens.reporting.*;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.client.gui.screens.telemetry.TelemetryInfoScreen;
import net.minecraft.client.gui.screens.worldselection.*;
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
        LOOKUP.put(FONT_SETTINGS, FontOptionsScreen.class);
        LOOKUP.put(CREDITS_AND_ATTRIBUTION, CreditsAndAttributionScreen.class);
        LOOKUP.put(ENDGAME, WinScreen.class);
        LOOKUP.put(CHAT, ChatScreen.class);
        LOOKUP.put(CREATIVE_INVENTORY, CreativeModeInventoryScreen.class);
        LOOKUP.put(OPEN_TO_LAN, ShareToLanScreen.class);
        LOOKUP.put(ADVANCEMENTS, AdvancementsScreen.class);
        LOOKUP.put(PACK_SELECTION, PackSelectionScreen.class);
        LOOKUP.put(SERVER_LINKS, ServerLinksDialogScreen.class);
        LOOKUP.put(CRAFTING_TABLE, CraftingScreen.class);
        LOOKUP.put(FURNACE, FurnaceScreen.class);
        LOOKUP.put(BLAST_FURNACE, BlastFurnaceScreen.class);
        LOOKUP.put(ANVIL, AnvilScreen.class);
        LOOKUP.put(LOOM, LoomScreen.class);
        LOOKUP.put(ENCHANTING_TABLE, EnchantmentScreen.class);
        LOOKUP.put(HOPPER, HopperScreen.class);
        LOOKUP.put(SMITHING_TABLE, SmithingScreen.class);
        LOOKUP.put(TRADING_SCREEN, MerchantScreen.class);
        LOOKUP.put(HORSE_INVENTORY, HorseInventoryScreen.class);
        LOOKUP.put(SIGN_EDIT, SignEditScreen.class);
        LOOKUP.put(HANGING_SIGN_EDIT, HangingSignEditScreen.class);
        LOOKUP.put(SMOKER, SmokerScreen.class);
        LOOKUP.put(STONECUTTER, StonecutterScreen.class);
        LOOKUP.put(LECTERN, LecternScreen.class);
        LOOKUP.put(BEACON, BeaconScreen.class);
        LOOKUP.put(CRAFTER, CrafterScreen.class);
        LOOKUP.put(BREWING_STAND, BrewingStandScreen.class);
        LOOKUP.put(STRUCTURE_BLOCK, StructureBlockEditScreen.class);
        LOOKUP.put(BOOK_VIEW, BookViewScreen.class);
        LOOKUP.put(BOOK_EDIT, BookEditScreen.class);
        LOOKUP.put(CARTOGRAPHY_TABLE, CartographyTableScreen.class);
        LOOKUP.put(COMMAND_BLOCK_EDIT, CommandBlockEditScreen.class);
        LOOKUP.put(MINECART_COMMAND_BLOCK_EDIT, MinecartCommandBlockEditScreen.class);
        LOOKUP.put(GRINDSTONE, GrindstoneScreen.class);
        LOOKUP.put(JIGSAW_BLOCK_EDIT, JigsawBlockEditScreen.class);
        LOOKUP.put(DEATH, DeathScreen.class);
        LOOKUP.put(IN_BED_CHAT, InBedChatScreen.class);
        LOOKUP.put(BACKUP_CONFIRM, BackupConfirmScreen.class);
        LOOKUP.put(DEBUG_OPTIONS, DebugOptionsScreen.class);
        LOOKUP.put(REPORT_PLAYER, ReportPlayerScreen.class);
        LOOKUP.put(REPORT_SELECT, ChatSelectionScreen.class);
        LOOKUP.put(REPORT_NICKNAME, NameReportScreen.class);
        LOOKUP.put(REPORT_SKIN, SkinReportScreen.class);
        LOOKUP.put(REPORT, ChatReportScreen.class);
        LOOKUP.put(REPORT_REASON_SELECTION, ReportReasonSelectionScreen.class);
        LOOKUP.put(STATISTICS, StatsScreen.class);
        LOOKUP.put(TELEMETRY_INFO, TelemetryInfoScreen.class);
        LOOKUP.put(EDIT_GAME_RULES, EditGameRulesScreen.class);
        LOOKUP.put(EDIT_WORLD, EditWorldScreen.class);
        LOOKUP.put(EXPERIMENTS, ExperimentsScreen.class);
        LOOKUP.put(EXPERIMENTAL_CONFIRM, ConfirmExperimentalFeaturesScreen.class);
        LOOKUP.put(OPTIMIZE_WORLD, OptimizeWorldScreen.class);
        LOOKUP.put(CODE_OF_CONDUCT, CodeOfConductScreen.class);
        LOOKUP.put(RECONFIGURATING, ServerReconfigScreen.class);
        LOOKUP.put(GAME_MODE_SWITCHER, GameModeSwitcherScreen.class);
        LOOKUP.put(ONLINE_OPTIONS, OnlineOptionsScreen.class);
        LOOKUP.put(NAUTILUS, NautilusInventoryScreen.class);
        LOOKUP.put(SHULKER_BOX, ShulkerBoxScreen.class);
        LOOKUP.put(UNSUPPORTED_GRAPHICS_WARNING, UnsupportedGraphicsWarningScreen.class);
        LOOKUP.put(SOCIAL_INTERACTIONS, SocialInteractionsScreen.class);
        LOOKUP.put(CONFIRM_LINK, ConfirmLinkScreen.class);
        LOOKUP.put(CREATE_BUFFET_WORLD, CreateBuffetWorldScreen.class);
        LOOKUP.put(CREATE_FLAT_WORLD, CreateFlatWorldScreen.class);
        LOOKUP.put(DATAPACK_LOAD_FAILURE, DatapackLoadFailureScreen.class);
        LOOKUP.put(DEMO_INTRO, DemoIntroScreen.class);
        LOOKUP.put(GAME_MENU, PauseScreen.class);
        LOOKUP.put(RECOVER_WORLD_DATA, RecoverWorldDataScreen.class);
        LOOKUP.put(PRESET_FLAT_WORLD, PresetFlatWorldScreen.class);
    }
}
