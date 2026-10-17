package pl.olafcio.avoid.net.services.list.session;

public class AuthException extends Exception {
    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Exception parent) {
        super(message, parent);
    }
}
