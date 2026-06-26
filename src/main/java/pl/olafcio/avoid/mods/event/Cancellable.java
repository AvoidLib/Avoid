package pl.olafcio.avoid.mods.event;

public abstract class Cancellable {
    private boolean cancelled
                    = false;

    public final void cancel() {
        setCancelled(true);
    }

    public void setCancelled(boolean value) {
        this.cancelled = value;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }
}
