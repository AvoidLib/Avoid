package pl.olafcio.avoid_impl.net.player.gamemode;

import net.minecraft.world.level.GameType;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.player.gamemode.GameMode;

@Native
@ApiStatus.Internal
public final class GameModeNative {
    @ApiStatus.Internal
    private GameModeNative() {}

    public static GameType convert(GameMode gamemode) {
        return GameType.byId(gamemode.getIndex());
    }

    public static GameMode convertFrom(GameType gamemode) {
        return GameMode.fromID(gamemode.getId());
    }
}
