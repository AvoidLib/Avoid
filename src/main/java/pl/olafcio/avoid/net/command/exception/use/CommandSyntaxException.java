package pl.olafcio.avoid.net.command.exception.use;

public class CommandSyntaxException extends Exception {
    public CommandSyntaxException(Exception exception) {
        super(exception);
    }
}
