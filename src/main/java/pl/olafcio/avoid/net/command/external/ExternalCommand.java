package pl.olafcio.avoid.net.command.external;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.command.executor.Executor;

import java.util.function.Predicate;

@ApiStatus.Experimental
public interface ExternalCommand {
    String getName();
    String getUsage();
    String[] getExamples();
    Predicate<Executor> getRequirement() throws ExtendedExecutorException;
}
