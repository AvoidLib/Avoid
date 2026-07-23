package pl.olafcio.avoid.net.fog.delta;

import net.minecraft.client.DeltaTracker;

@SuppressWarnings("ClassCanBeRecord")
public final class TickTracker {
    private final DeltaTracker deltaTracker;

    TickTracker(DeltaTracker deltaTracker) {
        this.deltaTracker = deltaTracker;
    }

    public float getTick() {
        return deltaTracker.getGameTimeDeltaTicks();
    }

    public float getPartialTick(boolean bl) {
        return deltaTracker.getGameTimeDeltaPartialTick(bl);
    }

    public float getRealtimeTick() {
        return deltaTracker.getRealtimeDeltaTicks();
    }
}
