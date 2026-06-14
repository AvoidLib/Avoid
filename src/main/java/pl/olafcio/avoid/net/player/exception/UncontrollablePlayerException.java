package pl.olafcio.avoid.net.player.exception;

import pl.olafcio.avoid.ImproperEnvironment;

public class UncontrollablePlayerException extends ImproperEnvironment {
    public UncontrollablePlayerException(String message) {
        super(message);
    }
}
