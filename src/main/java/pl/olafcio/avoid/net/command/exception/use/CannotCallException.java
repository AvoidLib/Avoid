package pl.olafcio.avoid.net.command.exception.use;

/**
 * An exception thrown when a command cannot be called due to a reflection failure.
 */
public class CannotCallException extends RuntimeException {
    public CannotCallException(String message, Exception parent) {
        super(message, parent);
    }
}
