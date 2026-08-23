package pl.olafcio.avoid.net.entity_type;

public class UnknownEntityTypeError extends RuntimeException {
    public UnknownEntityTypeError(String message) {
        super(message);
    }
}
