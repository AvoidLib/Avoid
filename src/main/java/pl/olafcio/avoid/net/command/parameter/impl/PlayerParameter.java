package pl.olafcio.avoid.net.command.parameter.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.server.Server;
import pl.olafcio.avoid.net.chat.component.Colors;
import pl.olafcio.avoid.net.chat.component.Components;
import pl.olafcio.avoid.net.command.exception.use.CommandSyntaxException;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.command.handling.Usage;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;
import pl.olafcio.avoid.net.player.Player;

public class PlayerParameter extends CommandParameter<Player> {
    public PlayerParameter(String name) {
        super(name);
    }

    @Override
    @NotNull
    public Player parse(String text) throws CommandSyntaxException {
        var player = Server.getPlayer(text);
        if (player == null)
            throw new CommandSyntaxException("Player not found");

        return player;
    }

    @Override
    @Nullable
    public String[] tabcomplete() {
        return Server.getPlayerNicks();
    }

    @Override
    public boolean sendSyntaxException(@NotNull Executor executor, @NotNull Usage ctx) {
        executor.sendMessage(Components.literal("No player was found")
                                       .color(Colors.RED));

        return true;
    }
}
