package pl.olafcio.avoid.net.command.exception.use;

/**
 * An exception thrown when an argument value couldn't be parsed.
 */
public class CommandSyntaxException extends Exception {
    public CommandSyntaxException(Exception exception) {
        super(exception);
    }
}
