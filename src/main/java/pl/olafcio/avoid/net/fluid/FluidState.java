package pl.olafcio.avoid.net.fluid;

public abstract class FluidState {
    public abstract boolean isSource();
    public abstract boolean isEmpty();
    public abstract boolean isRandomlyTicking();
    public abstract boolean isFalling();
    public abstract int getLevel();
}
