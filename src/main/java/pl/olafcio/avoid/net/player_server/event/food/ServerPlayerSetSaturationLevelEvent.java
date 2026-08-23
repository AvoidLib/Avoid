package pl.olafcio.avoid.net.player_server.event.food;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.mods.event_group.PlayerEvent;
import pl.olafcio.avoid.net.player.Player;

@ApiStatus.Experimental
public final class ServerPlayerSetSaturationLevelEvent extends Cancellable implements PlayerEvent {
    private final Player player;

    private float value;
    private final float defaultValue;
    private final float prevValue;

    public ServerPlayerSetSaturationLevelEvent(Player player, float value, float prevValue) {
        this.player = player;
        this.value =
        this.defaultValue = value;
        this.prevValue = prevValue;
    }

    public Player getPlayer() {
        return player;
    }

    public float getLevel() {
        return value;
    }

    public void setLevel(float value) {
        this.value = value;
    }

    public float getDefaultLevel() {
        return defaultValue;
    }

    public float getPreviousLevel() {
        return prevValue;
    }

    public boolean isLevelChanged() {
        return value != defaultValue;
    }
}
