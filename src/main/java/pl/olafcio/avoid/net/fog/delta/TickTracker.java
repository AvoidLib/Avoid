package pl.olafcio.avoid.net.fog.delta;

public abstract class TickTracker {
    public abstract float getTick();
    public abstract float getPartialTick(boolean ignoreFreeze);
    public abstract float getRealtimeTick();
}
