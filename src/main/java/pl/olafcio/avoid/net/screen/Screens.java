package pl.olafcio.avoid.net.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.ImproperEnvironment;

import java.util.function.Supplier;

/**
 * A namespace used to manage client screen state.
 */
@ApiStatus.Experimental
public final class Screens {
    @ApiStatus.Internal
    private Screens() {}

    /**
     * Replaces/overwrites a screen with another one.</br>
     * This effectively causes the new screen to open every time the old one does.
     */
    public static void overwrite(ScreenMarker oldScreen, Screen newScreen) {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT)
            throw new ImproperEnvironment("Cannot Screens#overwrite from the server!");

        ScreensNative.overwrite(oldScreen, newScreen);
    }

    /**
     * Replaces/overwrites a screen with another one.</br>
     * This effectively causes the new screen to open every time the old one does.
     */
    public static void overwrite(ScreenMarker oldScreen, Supplier<Screen> newScreen) {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT)
            throw new ImproperEnvironment("Cannot Screens#overwrite from the server!");

        ScreensNative.overwrite(oldScreen, newScreen);
    }

    /**
     * Replaces/overwrites a screen with another one.</br>
     * This effectively causes the new screen to open every time the old one does.
     */
    public static void overwrite(ScreenMarker oldScreen, Class<? extends Screen> newScreen) {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT)
            throw new ImproperEnvironment("Cannot Screens#overwrite from the server!");

        ScreensNative.overwrite(oldScreen, newScreen);
    }

    /**
     * Sets the active screen.
     */
    public static void open(Screen newScreen) {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT)
            throw new ImproperEnvironment("Cannot Screens#open from the server!");

        ScreensNative.open(newScreen);
    }

    // TODO: Marked-screen (transformed) arguments and/or opening helpers

    /**
     * Sets the active screen.
     */
    public static void open(ScreenMarker newScreen) {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT)
            throw new ImproperEnvironment("Cannot Screens#open from the server!");

        ScreensNative.open(newScreen);
    }

    /**
     * Closes the active screen.<br/>
     * Ingame, this causes the player to go back to the game.<br/>
     * Otherwise, this brings up the title screen.
     */
    public static void close() {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT)
            throw new ImproperEnvironment("Cannot Screens#close from the server!");

        ScreensNative.close();
    }

    // TODO: GUI Layers (like in NeoForge); wouldn't those conflict with NeoForge, however?
}
