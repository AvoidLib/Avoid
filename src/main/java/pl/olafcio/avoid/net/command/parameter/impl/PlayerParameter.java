package pl.olafcio.avoid.net.command.parameter.impl;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.net.chat.component.Colors;
import pl.olafcio.avoid.net.chat.component.Components;
import pl.olafcio.avoid.net.command.exception.use.CommandSyntaxException;
import pl.olafcio.avoid.net.command.executor.Executor;
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

    @Override
    public boolean sendSyntaxException(@NotNull Executor executor, @NotNull CommandContext<CommandSourceStack> ctx) {
        executor.sendMessage(Components.literal("No player was found")
                                       .color(Colors.RED));

        return true;
    }
}
