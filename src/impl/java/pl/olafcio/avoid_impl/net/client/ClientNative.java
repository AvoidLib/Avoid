package pl.olafcio.avoid_impl.net.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid_impl.mixininterface.IScreen;
import pl.olafcio.avoid_impl.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.client.server.PlayerEntry;
import pl.olafcio.avoid.net.client.server.ServerEntry;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid_impl.net.entity.EntityNative;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid_impl.net.screen.AvoidScreen;
import pl.olafcio.avoid_impl.net.screen.NativeScreenNative;
import pl.olafcio.avoid.net.screen.Screen;
import pl.olafcio.avoid.net.screen.font.Font;
import pl.olafcio.avoid.net.screen.font.FontNative;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.WorldNative;

@Native
@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public final class ClientNative {
    @ApiStatus.Internal
    private ClientNative() {}

    @Nullable
    public static String getLauncherBrand() {
        return Minecraft.getLauncherBrand();
    }

    @Nullable
    public static Player getPlayer() {
        var player = Minecraft.getInstance().player;
        if (player == null)
            return null;

        return PlayerNative.convertFrom(player);
    }

    @Nullable
    public static Entity getCamera() {
        var entity = Minecraft.getInstance().getCameraEntity();
        if (entity == null)
            return null;

        return EntityNative.convertFrom(entity);
    }

    @Nullable
    public static World getWorld() {
        var level = Minecraft.getInstance().level;
        if (level == null)
            return null;

        return WorldNative.make(level);
    }

    @Nullable
    public static Screen getScreen() {
        var screen = Minecraft.getInstance().screen;
        if (screen == null)
            return null;

        return screen instanceof AvoidScreen avoid
                ? avoid.screen
                : NativeScreenNative.create((IScreen) screen);
    }

    public static Font getFont() {
        return FontNative.convertFrom(Minecraft.getInstance().font);
    }

    public static int getFPS() {
        return Minecraft.getInstance().getFps();
    }

    public static boolean isWireframe() {
        return Minecraft.getInstance().wireframe;
    }

    public static boolean isWindowActive() {
        return Minecraft.getInstance().isWindowActive();
    }

    public static boolean inSingleplayer() {
        return Minecraft.getInstance().isLocalServer();
    }

    public static boolean inSingleplayerPublished() {
        return Minecraft.getInstance().isSingleplayer();
    }

    public static boolean isNameBanned() {
        return Minecraft.getInstance().isNameBanned();
    }

    @Nullable
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

    public static boolean isMoving() {
        var player = Minecraft.getInstance().player;
        if (player == null)
            return false;

        return player.input.getMoveVector().lengthSquared() > 0.0F;
    }

    public static boolean isHandsBusy() {
        var player = Minecraft.getInstance().player;
        if (player == null)
            return false;

        return player.isHandsBusy();
    }
}
