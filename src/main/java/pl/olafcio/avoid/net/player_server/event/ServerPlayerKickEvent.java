package pl.olafcio.avoid.net.player_server.event;

import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player.gamemode.GameMode;

public final class ServerPlayerKickEvent extends Cancellable {
    private final Player player;
    private final BaseComponent<?> originalReason;

    private BaseComponent<?> reason;
    private boolean reasonChanged = false;

    public ServerPlayerKickEvent(Player player, BaseComponent<?> reason) {
        this.player = player;
        this.reason =
        this.originalReason = reason;
    }

    public Player getPlayer() {
        return player;
    }

    public BaseComponent<?> getReason() {
        return reason;
    }

    public BaseComponent<?> getOriginalReason() {
        return originalReason;
    }

    public void setReason(BaseComponent<?> value) {
        this.reason = value;
        this.reasonChanged = true;
    }

    public boolean isReasonChanged() {
        return reasonChanged;
    }
}
