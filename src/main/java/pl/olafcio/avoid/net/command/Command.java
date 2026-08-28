package pl.olafcio.avoid.net.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.chat.component.Colors;
import pl.olafcio.avoid.net.chat.component.Components;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.command.handling.CommandHandler;
import pl.olafcio.avoid.net.command.handling.Usage;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;

import java.util.Map;

/**
 * Specifies the extending class is ready to register as a server command.
 */
@NeverRemoval
public abstract class Command {
    @NeverRemoval
    public void sendSyntaxException(
            @NotNull Executor executor,
            @NotNull Usage ctx,
            @Nullable CommandParameter<?> param
    ) {
        // If you just want it simple:
        // > executor.sendMessage(Components.literal("§cIncorrect syntax"));

        if (param != null && param.sendSyntaxException(executor, ctx))
            return;

        executor.sendMessage(Components.translationFallback("command.unknown.command", "Unknown or incomplete command. See below for error")
                                       .color(Colors.RED)
                                       .append(Components.literal("\n§7" + ctx.getInput()))
                                       .append(Components.translationFallback("command.context.here", "<--[HERE]").color(Colors.RED).italic(true)));
    }

    /**
     * @deprecated This method used a minecraft type in the 'ctx' parameter.<br/>
     *             {@link #sendSyntaxException(Executor, Usage, CommandParameter)} is recommended instead.
     */
    @NeverRemoval
    @Deprecated(since = "v1.18")
    public void sendSyntaxException(
            @NotNull Executor executor,
            @NotNull Object ctx,
            @Nullable CommandParameter<?> param
    ) {
        this.sendSyntaxException(executor, new Usage(Map.of(), executor), param);
    }

    /**
     * <b>Note:</b> This method works only after registering the class.
     */
    @NeverRemoval
    public final String getName() {
        return CommandManager.get(this).name();
    }

    /**
     * <b>Note:</b> This method works only after registering the class.
     */
    @NeverRemoval
    public final SyntaxTree getSyntaxTree() {
        return CommandManager.get(this).syntaxes();
    }

    /**
     * <b>Note:</b> This method works only after registering the class.
     */
    @NeverRemoval
    public final CommandHandler getUnknownHandler() {
        return CommandManager.get(this).unknownhandler();
    }
}
