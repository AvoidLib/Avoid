package pl.olafcio.avoid.net.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.chat.component.Colors;
import pl.olafcio.avoid.net.chat.component.Components;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.command.handling.CommandHandler;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;

/**
 * Specifies the extending class is ready to register as a server command.
 */
@ApiStatus.Experimental
public abstract class Command {
    public void sendSyntaxException(
            @NotNull Executor executor,
            @NotNull CommandContext<CommandSourceStack> ctx,
            @Nullable CommandParameter<?> param
    ) {
        // If you just want it simple:
        // > executor.sendMessage(Components.literal("§cIncorrect syntax"));

        // TODO: Use translation instead

        if (param != null && param.sendSyntaxException(executor, ctx))
            return;

        executor.sendMessage(Components.literal("§cUnknown or incomplete command. See below for error")
                                       .append(Components.literal("\n§7" + ctx.getInput()))
                                       .append(Components.literal("<--[HERE]").color(Colors.RED).italic(true)));
    }

    /**
     * <b>Note:</b> This method works only after registering the class.
     */
    public final String getName() {
        return CommandManager.get(this).name();
    }

    /**
     * <b>Note:</b> This method works only after registering the class.
     */
    public final SyntaxTree getSyntaxTree() {
        return CommandManager.get(this).syntaxes();
    }

    /**
     * <b>Note:</b> This method works only after registering the class.
     */
    public final CommandHandler getUnknownHandler() {
        return CommandManager.get(this).unknownhandler();
    }
}
