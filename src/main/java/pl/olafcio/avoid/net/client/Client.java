package pl.olafcio.avoid.net.client;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.client.server.ServerEntry;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.screen.Screen;
import pl.olafcio.avoid.net.world.World;

/**
 * A class containing static utilities from the client.<br/>
 * All methods throw an error when called on the server.
 */
@NeverRemoval
public final class Client {
    @ApiStatus.Internal
    private Client() {}

    /**
     * Gets the current launcher brand.<br/>
     * This should be something like {@code ATLauncher}, I think.
     */
    @Nullable
    @ApiStatus.Experimental
    public static String getLauncherBrand()  {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getLauncherBrand() on the server");

        return ClientNative.getLauncherBrand();
    }

    /**
     * Gets the local player instance.<br/>
     * This is the player object for the client controlled player.
     */
    @Nullable
    @NeverRemoval
    public static Player getPlayer() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getPlayer() on the server");

        return ClientNative.getPlayer();
    }

    /**
     * Gets the camera entity.<br/>
     * This is the entity object that the client has the FOV from.<br/><br/>
     * This is usually equal to the {@linkplain Client#getPlayer() local player object}.
     */
    @Nullable
    @NeverRemoval
    public static Entity getCamera() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getCamera() on the server");

        return ClientNative.getCamera();
    }

    /**
     * Gets the local world instance.<br/>
     * This is the world object that the {@linkplain Client#getPlayer() local player} is in.
     */
    @Nullable
    @NeverRemoval
    public static World getWorld() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getWorld() on the server");

        return ClientNative.getWorld();
    }

    /**
     * Gets the active screen instance.<br/>
     * This is kinda the "view" that the client is displaying.
     */
    @Nullable
    @NeverRemoval
    public static Screen getScreen() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getScreen() on the server");

        return ClientNative.getScreen();
    }

    /**
     * Gets the current FPS <i>(frames per second)</i>.
     */
    @NeverRemoval
    public static int getFPS() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getFPS() on the server");

        return ClientNative.getFPS();
    }

    @ApiStatus.Experimental
    public static boolean isWireframe() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#isWireframe() on the server");

        return ClientNative.isWireframe();
    }

    @ApiStatus.Experimental
    public static boolean isWindowActive() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#isWindowActive() on the server");

        return ClientNative.isWindowActive();
    }

    @ApiStatus.Experimental
    public static boolean inSingleplayer() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#inSingleplayer() on the server");

        return ClientNative.inSingleplayer();
    }

    @ApiStatus.Experimental
    public static boolean inSingleplayerPublished() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#inSingleplayerPublished() on the server");

        return ClientNative.inSingleplayerPublished();
    }

    @ApiStatus.Experimental
    public static boolean isNameBanned() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#isNameBanned() on the server");

        return ClientNative.isNameBanned();
    }

    @Nullable
    @ApiStatus.Experimental
    public static ServerEntry getCurrentServer() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getCurrentServer() on the server");

        return ClientNative.getCurrentServer();
    }

    @ApiStatus.Experimental
    public boolean isMoving() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#isMoving() on the server");

        return ClientNative.isMoving();
    }

    @ApiStatus.Experimental
    public boolean isHandsBusy() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#isHandsBusy() on the server");

        return ClientNative.isHandsBusy();
    }
}
