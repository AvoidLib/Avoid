package pl.olafcio.avoid.net.player_server.event.food;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.player.Player;

@ApiStatus.Experimental
public final class ServerPlayerSetFoodLevelEvent extends Cancellable {
    private final Player player;

    private int value;
    private final int defaultValue;
    private final int prevValue;

    public ServerPlayerSetFoodLevelEvent(Player player, int value, int prevValue) {
        this.player = player;
        this.value =
        this.defaultValue = value;
        this.prevValue = prevValue;
    }

    public Player getPlayer() {
        return player;
    }

    public int getLevel() {
        return value;
    }

    public void setLevel(int value) {
        this.value = value;
    }

    public int getDefaultLevel() {
        return defaultValue;
    }

    public int getPreviousLevel() {
        return prevValue;
    }

    public boolean isLevelChanged() {
        return value != defaultValue;
    }
}
