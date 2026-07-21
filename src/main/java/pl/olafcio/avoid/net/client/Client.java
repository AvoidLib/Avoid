package pl.olafcio.avoid.net.client;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.mixininterface.IScreen;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.client.server.PlayerEntry;
import pl.olafcio.avoid.net.client.server.ServerEntry;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.screen.AvoidScreen;
import pl.olafcio.avoid.net.screen.NativeScreenNative;
import pl.olafcio.avoid.net.screen.Screen;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.WorldNative;

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
        var player = Minecraft.getInstance().player;
        if (player == null)
            return null;

        return PlayerNative.convertFrom(player);
    }

    @Nullable
    @ApiStatus.Experimental
    public static World getWorld() {
        var level = Minecraft.getInstance().level;
        if (level == null)
            return null;

        return WorldNative.make(level);
    }

    @Nullable
    @ApiStatus.Experimental
    public static Screen getScreen() {
        var screen = Minecraft.getInstance().screen;
        if (screen == null)
            return null;

        return screen instanceof AvoidScreen avoid
                ? avoid.screen
                : NativeScreenNative.create((IScreen) screen);
    }

    @ApiStatus.Experimental
    public static int getFPS() {
        return Minecraft.getInstance().getFps();
    }

    @ApiStatus.Experimental
    public static boolean isWireframe() {
        return Minecraft.getInstance().wireframe;
    }

    @ApiStatus.Experimental
    public static boolean isWindowActive() {
        return Minecraft.getInstance().isWindowActive();
    }

    @ApiStatus.Experimental
    public static boolean inSingleplayer() {
        return Minecraft.getInstance().isLocalServer();
    }

    @ApiStatus.Experimental
    public static boolean inSingleplayerPublished() {
        return Minecraft.getInstance().isSingleplayer();
    }

    @ApiStatus.Experimental
    public static boolean isNameBanned() {
        return Minecraft.getInstance().isNameBanned();
    }

    @Nullable
    @ApiStatus.Experimental
    public static ServerEntry getCurrentServer() {
        var entry = Minecraft.getInstance().getCurrentServer();
        if (entry == null)
            return null;

        return new ServerEntry(
                entry.ip,
                entry.name,
                COFromNative.from(entry.motd),
                entry.ping,
                entry.playerList.stream().map(COFromNative::from).toList(),
                entry.protocol,
                COFromNative.from(entry.status),
                COFromNative.from(entry.version),
                entry.players == null ? null : entry.players.max(),
                entry.players == null ? null : entry.players.online(),
                entry.players == null ? null : entry.players.sample().stream().map(obj -> new PlayerEntry(obj.id(), obj.name())).toList(),
                entry.getIconBytes()
        );
    }
}
