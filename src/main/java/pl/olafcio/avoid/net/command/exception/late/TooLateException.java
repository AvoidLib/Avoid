package pl.olafcio.avoid.net.command.exception.late;

/**
 * An exception thrown when someone tried to register a command after they have been initialized (and the registry frozen).
 */
public class TooLateException extends RuntimeException {
    public TooLateException(String message) {
        super(message);
    }
}
