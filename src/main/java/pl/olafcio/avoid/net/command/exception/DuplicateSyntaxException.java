package pl.olafcio.avoid.net.command.exception;

import pl.olafcio.avoid.net.command.annotation.Syntax;

/**
 * An exception thrown when the same <a style="color: #3887a1">@{@linkplain Syntax}</a> is used twice.
 */
public class DuplicateSyntaxException extends RuntimeException {
    public DuplicateSyntaxException(String message) {
        super(message);
    }
}
