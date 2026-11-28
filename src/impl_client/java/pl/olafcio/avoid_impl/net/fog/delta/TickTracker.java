package pl.olafcio.avoid_impl.net.fog.delta;

import net.minecraft.client.DeltaTracker;
import pl.olafcio.avoid.annotations.dist.MCEnvironment;

public final class TickTracker extends pl.olafcio.avoid.net.fog.delta.TickTracker {
    private final Object deltaTracker;

    TickTracker(Object deltaTracker) {
        this.deltaTracker = deltaTracker;
    }

    @MCEnvironment("client")
    private DeltaTracker __dt() {
        return (DeltaTracker) deltaTracker;
    }

    @Override
    public float getTick() {
        return __getTick();
    }

    @Override
    public float getPartialTick(boolean ignoreFreeze) {
        return __getPartialTick(ignoreFreeze);
    }

    @Override
    public float getRealtimeTick() {
        return __getRealtimeTick();
    }

    @MCEnvironment("client")
    private float __getTick() {
        return __dt().getGameTimeDeltaTicks();
    }

    @MCEnvironment("client")
    private float __getPartialTick(boolean ignoreFreeze) {
        return __dt().getGameTimeDeltaPartialTick(ignoreFreeze);
    }

    @MCEnvironment("client")
    private float __getRealtimeTick() {
        return __dt().getRealtimeDeltaTicks();
    }
}
