package pl.olafcio.avoid.net.command.exception.late;

public class TooLateException extends RuntimeException {
    public TooLateException(String message) {
        super(message);
    }
}
