package pl.olafcio.avoid.net.fog.delta;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;

@SuppressWarnings("ClassCanBeRecord")
public final class TickTracker {
    private final Object deltaTracker;

    TickTracker(Object deltaTracker) {
        this.deltaTracker = deltaTracker;
    }

    @Environment(EnvType.CLIENT)
    private DeltaTracker __dt() {
        return (DeltaTracker) deltaTracker;
    }

    public float getTick() {
        return __getTick();
    }

    public float getPartialTick(boolean ignoreFreeze) {
        return __getPartialTick(ignoreFreeze);
    }

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
