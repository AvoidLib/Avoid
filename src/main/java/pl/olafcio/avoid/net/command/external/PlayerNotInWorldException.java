package pl.olafcio.avoid.net.command.external;

public class PlayerNotInWorldException extends RuntimeException {
    public PlayerNotInWorldException(String message) {
        super(message);
    }
}
