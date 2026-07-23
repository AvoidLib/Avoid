package pl.olafcio.avoid.net.client;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.net.client.server.ServerEntry;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.screen.Screen;
import pl.olafcio.avoid.net.world.World;

/**
 * A class containing static utilities from the client.<br/>
 * All methods throw an error when called on the server.
 */
@ApiStatus.Experimental
public final class Client {
    @ApiStatus.Internal
    private Client() {}

    @Nullable
    @ApiStatus.Experimental
    public static Player getPlayer() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getPlayer() on the server");

        return ClientNative.getPlayer();
    }

    @Nullable
    @ApiStatus.Experimental
    public static World getWorld() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getPlayer() on the server");

        return ClientNative.getWorld();
    }

    @Nullable
    @ApiStatus.Experimental
    public static Screen getScreen() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getPlayer() on the server");

        return ClientNative.getScreen();
    }

    @ApiStatus.Experimental
    public static int getFPS() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getPlayer() on the server");

        return ClientNative.getFPS();
    }

    @ApiStatus.Experimental
    public static boolean isWireframe() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getPlayer() on the server");

        return ClientNative.isWireframe();
    }

    @ApiStatus.Experimental
    public static boolean isWindowActive() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getPlayer() on the server");

        return ClientNative.isWindowActive();
    }

    @ApiStatus.Experimental
    public static boolean inSingleplayer() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getPlayer() on the server");

        return ClientNative.inSingleplayer();
    }

    @ApiStatus.Experimental
    public static boolean inSingleplayerPublished() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getPlayer() on the server");

        return ClientNative.inSingleplayerPublished();
    }

    @ApiStatus.Experimental
    public static boolean isNameBanned() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getPlayer() on the server");

        return ClientNative.isNameBanned();
    }

    @Nullable
    @ApiStatus.Experimental
    public static ServerEntry getCurrentServer() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            throw new ImproperEnvironment("Cannot Client#getPlayer() on the server");

        return ClientNative.getCurrentServer();
    }
}
