package pl.olafcio.avoid.net.command.external;

import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.player.exception.UncontrollablePlayerException;

import java.util.function.Predicate;

public interface ClientCommand extends ExternalCommand {
    Requirement getRequirement() throws UncontrollablePlayerException;

    interface Requirement extends Predicate<Executor> {
        boolean test();
    }
}
