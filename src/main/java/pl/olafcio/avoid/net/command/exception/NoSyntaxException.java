package pl.olafcio.avoid.net.command.exception;

import pl.olafcio.avoid.net.command.annotation.Syntax;

/**
 * An exception thrown when no <a style="color: #3887a1">@{@linkplain Syntax}</a> methods are present.
 */
public class NoSyntaxException extends RuntimeException {
    public NoSyntaxException(String message) {
        super(message);
    }
}
