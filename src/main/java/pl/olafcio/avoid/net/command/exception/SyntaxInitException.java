package pl.olafcio.avoid.net.command.exception;

/**
 * An exception thrown when a parameter type instance couldn't be created.
 */
public class SyntaxInitException extends RuntimeException {
    public SyntaxInitException(String message, Exception parent) {
        super(message, parent);
    }
}
