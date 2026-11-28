package pl.olafcio.avoid_impl.net.client;

import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.client.server.ServerEntry;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.screen.Screen;
import pl.olafcio.avoid.net.screen.font.Font;
import pl.olafcio.avoid.net.services.Services;
import pl.olafcio.avoid.net.world.World;

public enum ClientNative {
    ;

    public static Services getServices() {
        throw new UnsupportedOperationException();
    }

    public static @Nullable String getLauncherBrand() {
        throw new UnsupportedOperationException();
    }

    public static @Nullable Player getPlayer() {
        throw new UnsupportedOperationException();
    }

    public static @Nullable Entity getCamera() {
        throw new UnsupportedOperationException();
    }

    public static @Nullable World getWorld() {
        throw new UnsupportedOperationException();
    }

    public static @Nullable Screen getScreen() {
        throw new UnsupportedOperationException();
    }

    public static Font getFont() {
        throw new UnsupportedOperationException();
    }

    public static int getFPS() {
        throw new UnsupportedOperationException();
    }

    public static boolean isWireframe() {
        throw new UnsupportedOperationException();
    }

    public static boolean isWindowActive() {
        throw new UnsupportedOperationException();
    }

    public static boolean inSingleplayer() {
        throw new UnsupportedOperationException();
    }

    public static boolean inSingleplayerPublished() {
        throw new UnsupportedOperationException();
    }

    public static boolean isNameBanned() {
        throw new UnsupportedOperationException();
    }

    public static @Nullable ServerEntry getCurrentServer() {
        throw new UnsupportedOperationException();
    }

    public static boolean isMoving() {
        throw new UnsupportedOperationException();
    }

    public static boolean isHandsBusy() {
        throw new UnsupportedOperationException();
    }
}
