package pl.olafcio.avoid.net.command.parameter.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.net.command.exception.use.CommandSyntaxException;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player.PlayerNative;

public class PlayerParameter extends CommandParameter<Player> {
    public PlayerParameter(String name) {
        super(name);
    }

    @Override
    @NotNull
    public Player parse(String text) throws CommandSyntaxException {
        var player = AvoidInternal.getServer().getPlayerList().getPlayer(text);
        if (player == null)
            throw new CommandSyntaxException("Player not found");

        return PlayerNative.convertFrom(player);
    }

    @Override
    @Nullable
    public String[] tabcomplete() {
        return AvoidInternal.getServer().getPlayerNames();
    }
}
