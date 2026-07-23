package pl.olafcio.avoid.net.player_server.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.player.Player;

@ApiStatus.Experimental
public final class ServerPlayerUpdateAbilitiesEvent extends Cancellable {
    public final Player player;
    public final boolean flying;

    public ServerPlayerUpdateAbilitiesEvent(Player player, boolean flying) {
        this.player = player;
        this.flying = flying;
    }
}
