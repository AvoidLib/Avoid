package pl.olafcio.avoid.net.entity_layer;

public class TooLateRegisterException extends RuntimeException {
    public TooLateRegisterException(String message) {
        super(message);
    }
}
