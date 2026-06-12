package pl.olafcio.avoid.mods.event;

public abstract class Cancellable {
    private boolean cancelled
                    = false;

    public void cancel() {
        this.cancelled = true;
    }

    public void setCancelled(boolean value) {
        this.cancelled = value;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }
}
