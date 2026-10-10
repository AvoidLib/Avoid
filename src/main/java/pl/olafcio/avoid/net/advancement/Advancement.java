package pl.olafcio.avoid.net.advancement;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.player.Player;

@ApiStatus.Experimental
public abstract class Advancement {
    public void onGrantClient(Player player) {}
    public void onGrantServer(Player player) {}
}
