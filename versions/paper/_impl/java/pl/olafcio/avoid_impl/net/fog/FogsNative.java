package pl.olafcio.avoid.net.fog;

import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.annotations.dist.Dist;
import pl.olafcio.avoid.annotations.dist.OnlyIn;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

@Native
@OnlyIn(Dist.CLIENT)
@ApiStatus.Internal
final class FogsNative {
    @ApiStatus.Internal
    private FogsNative() {}

    public static void register(Fog fog) {
        // Should never be called
    }
}
