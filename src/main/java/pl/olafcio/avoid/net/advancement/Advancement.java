package pl.olafcio.avoid.net.advancement;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.player.Player;

@ApiStatus.Experimental
public abstract class Advancement {
    /**
     * Invoked when the client has been notified that he has been granted the advancement.
     */
    public void onGrantClient(Player player) {}

    /**
     * Invoked when the player has been granted this advancement on the server.
     */
    public void onGrantServer(Player player) {}
}
