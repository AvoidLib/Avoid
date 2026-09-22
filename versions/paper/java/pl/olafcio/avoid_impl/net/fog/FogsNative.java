package pl.olafcio.avoid_impl.net.fog;

import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.annotations.dist.Dist;
import pl.olafcio.avoid.annotations.dist.OnlyIn;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.net.fog.Fog;

@Native
@OnlyIn(Dist.CLIENT)
@ApiStatus.Internal
public final class FogsNative {
    @ApiStatus.Internal
    private FogsNative() {}

    public static void register(Fog fog) {
        // Should never be called
    }
}
