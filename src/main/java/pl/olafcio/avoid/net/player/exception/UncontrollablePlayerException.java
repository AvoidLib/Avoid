package pl.olafcio.avoid.net.player.exception;

import pl.olafcio.avoid.errors.ImproperEnvironment;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

@NeverRemoval
public class UncontrollablePlayerException extends ImproperEnvironment {
    public UncontrollablePlayerException(String message) {
        super(message);
    }
}
