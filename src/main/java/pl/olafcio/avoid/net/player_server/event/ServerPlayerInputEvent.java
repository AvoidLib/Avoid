package pl.olafcio.avoid.net.player_server.event;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player_server.PlayerInput;

@NeverRemoval
public final class ServerPlayerInputEvent extends Cancellable {
    private final Player player;
    private final PlayerInput originalInput;

    private PlayerInput input;
    private boolean inputChanged = false;

    public ServerPlayerInputEvent(Player player, PlayerInput input) {
        this.player = player;
        this.input =
        this.originalInput = input;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerInput getInput() {
        return input;
    }

    public PlayerInput getOriginalInput() {
        return originalInput;
    }

    public void setInput(PlayerInput value) {
        this.input = value;
        this.inputChanged = true;
    }

    public boolean isInputChanged() {
        return inputChanged;
    }
}
