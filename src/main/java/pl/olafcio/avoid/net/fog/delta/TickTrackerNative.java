package pl.olafcio.avoid.net.fog.delta;

import net.minecraft.client.DeltaTracker;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class TickTrackerNative {
    @ApiStatus.Internal
    private TickTrackerNative() {}

    public static TickTracker create(DeltaTracker mc) {
        return new TickTracker(mc);
    }
}
