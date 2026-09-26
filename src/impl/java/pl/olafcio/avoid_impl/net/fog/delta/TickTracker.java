package pl.olafcio.avoid_impl.net.fog.delta;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;

public final class TickTracker extends pl.olafcio.avoid.net.fog.delta.TickTracker {
    private final Object deltaTracker;

    TickTracker(Object deltaTracker) {
        this.deltaTracker = deltaTracker;
    }

    @Environment(EnvType.CLIENT)
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

    @Environment(EnvType.CLIENT)
    private float __getTick() {
        return __dt().getGameTimeDeltaTicks();
    }

    @Environment(EnvType.CLIENT)
    private float __getPartialTick(boolean ignoreFreeze) {
        return __dt().getGameTimeDeltaPartialTick(ignoreFreeze);
    }

    @Environment(EnvType.CLIENT)
    private float __getRealtimeTick() {
        return __dt().getRealtimeDeltaTicks();
    }
}
