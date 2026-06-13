package pl.olafcio.avoid.net.player.gamemode;

import net.minecraft.world.level.GameType;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class GameModeNative {
    @ApiStatus.Internal
    private GameModeNative() {}

    public static GameType convert(GameMode gamemode) {
        return GameType.byId(gamemode.index);
    }

    public static GameMode convertFrom(GameType gamemode) {
        return GameMode.fromID(gamemode.getId());
    }
}
