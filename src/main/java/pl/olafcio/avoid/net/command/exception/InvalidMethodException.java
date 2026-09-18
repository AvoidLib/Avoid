package pl.olafcio.avoid.net.command.exception;

/**
 * An exception thrown when either:
 * <ul>
 *     <li>a {@link pl.olafcio.avoid.net.command.annotation.Syntax @Syntax} method has not 1 argument,</li>
 *     <li>a {@link pl.olafcio.avoid.net.command.annotation.Unknown @Unknown} method has not 1 argument,</li>
 *     <li>a {@link pl.olafcio.avoid.net.command.annotation.Tabcomplete @Tabcomplete} method has more than 1 argument.</li>
 * </ul>
 */
public class InvalidMethodException extends RuntimeException {
    public InvalidMethodException(String message) {
        super(message);
    }
}
