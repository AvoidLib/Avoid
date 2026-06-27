package pl.olafcio.avoid.net.player_server.event;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.player.gamemode.GameMode;
import pl.olafcio.avoid.net.player.Player;

@NeverRemoval
public final class ServerPlayerGameModeChangeEvent extends Cancellable {
    public final Player player;
    public final GameMode gamemode;

    public ServerPlayerGameModeChangeEvent(Player player, GameMode gamemode) {
        this.player = player;
        this.gamemode = gamemode;
    }
}
