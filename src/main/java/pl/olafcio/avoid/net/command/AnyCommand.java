package pl.olafcio.avoid.net.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.command.handling.CommandHandler;
import pl.olafcio.avoid.net.command.handling.Usage;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;

public interface AnyCommand {
    @NeverRemoval
    void sendSyntaxException(
            @NotNull Executor executor,
            @NotNull Usage ctx,
            @Nullable CommandParameter<?> param
    );

    @NeverRemoval
    @NotNull String getName();

    @NeverRemoval
    @NotNull String getUsage();

    @NeverRemoval
    String @NotNull[] getExamples();

    @NeverRemoval
    @NotNull SyntaxTree getSyntaxTree();

    @NeverRemoval
    @Nullable CommandHandler getUnknownHandler();
}
