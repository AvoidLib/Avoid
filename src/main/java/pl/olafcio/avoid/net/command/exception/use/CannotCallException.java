package pl.olafcio.avoid.net.command.exception.use;

public class CannotCallException extends RuntimeException {
    public CannotCallException(String message, Exception parent) {
        super(message, parent);
    }
}
