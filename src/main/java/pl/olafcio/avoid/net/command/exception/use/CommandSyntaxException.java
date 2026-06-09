package pl.olafcio.avoid.net.command.exception.use;

public class CommandSyntaxException extends RuntimeException {
    public CommandSyntaxException(Exception exception) {
        super(exception);
    }
}
