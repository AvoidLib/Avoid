package pl.olafcio.avoid.net.command;

import org.jetbrains.annotations.ApiStatus;
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
 * Specifies the extending class is ready to register as a client command.
 */
@ApiStatus.Experimental
public abstract class ClientCommand implements AnyCommand {
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
     * <b>Note:</b> This method works only after registering the class.
     */
    @NeverRemoval
    @NotNull
    public final String getName() {
        return ClientCommandManager.get(this).name();
    }

    /**
     * <b>Note:</b> This method works only after registering the class.
     */
    @NeverRemoval
    @NotNull
    public final String getUsage() {
        return ClientCommandManager.get(this).usage();
    }

    /**
     * <b>Note:</b> This method works only after registering the class.
     */
    @NeverRemoval
    public final String @NotNull[] getExamples() {
        return ClientCommandManager.get(this).examples();
    }

    /**
     * <b>Note:</b> This method works only after registering the class.
     */
    @NeverRemoval
    @NotNull
    public final SyntaxTree getSyntaxTree() {
        return ClientCommandManager.get(this).syntaxes();
    }

    /**
     * <b>Note:</b> This method works only after registering the class.
     */
    @NeverRemoval
    @Nullable
    public final CommandHandler getUnknownHandler() {
        return ClientCommandManager.get(this).unknownhandler();
    }
}
