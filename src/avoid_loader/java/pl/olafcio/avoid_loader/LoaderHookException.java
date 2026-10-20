package pl.olafcio.avoid_loader;

public class LoaderHookException extends RuntimeException {
    public LoaderHookException(String message) {
        super(message);
    }

    public LoaderHookException(String message, Throwable parent) {
        super(message, parent);
    }
}
