package pl.olafcio.avoid.net.services.list.session;

public class InsecureSignatureException extends Exception {
    public InsecureSignatureException(String message, Exception parent) {
        super(message, parent);
    }
}
