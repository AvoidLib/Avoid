package pl.olafcio.avoid.net.player_server.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player_server.values.HandType;

@ApiStatus.Experimental
public final class ServerPlayerSwingEvent extends Cancellable {
    public final Player player;
    public final HandType hand;

    public ServerPlayerSwingEvent(Player player, HandType hand) {
        this.player = player;
        this.hand = hand;
    }
}
