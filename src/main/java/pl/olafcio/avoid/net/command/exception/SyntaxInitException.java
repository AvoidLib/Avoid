package pl.olafcio.avoid.net.command.exception;

public class SyntaxInitException extends RuntimeException {
    public SyntaxInitException(String message, Exception parent) {
        super(message, parent);
    }
}
