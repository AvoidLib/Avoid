package pl.olafcio.avoid.net.command.exception;

import pl.olafcio.avoid.net.command.annotation.Syntax;

/**
 * An exception thrown when a <a style="color: #3887a1">@{@linkplain Syntax}</a> value was invalid and couldn't be parsed.
 */
public class InvalidSyntaxException extends RuntimeException {
    public InvalidSyntaxException(String message) {
        super(message);
    }
}
