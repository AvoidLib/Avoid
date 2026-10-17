package pl.olafcio.avoid.net.services.list.session;

public class AuthNotAvailableException extends AuthException {
    public AuthNotAvailableException(String message, Exception parent) {
        super(message, parent);
    }
}
