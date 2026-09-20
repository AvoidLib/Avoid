package pl.olafcio.avoid.net.command;

class NotFoundSyntaxException extends RuntimeException {
    public NotFoundSyntaxException(String message) {
        super(message);
    }
}
