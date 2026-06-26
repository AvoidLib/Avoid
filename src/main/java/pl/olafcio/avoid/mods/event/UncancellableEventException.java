package pl.olafcio.avoid.mods.event;

/**
 * Used by overrides of {@link Cancellable#setCancelled}.
 */
public class UncancellableEventException extends RuntimeException {
    public UncancellableEventException(String message) {
        super(message);
    }
}
