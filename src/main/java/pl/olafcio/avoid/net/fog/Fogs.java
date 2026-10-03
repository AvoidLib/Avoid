package pl.olafcio.avoid.net.fog;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.platform.AvoidWrappedLoader;
import pl.olafcio.avoid.util.RunningEnv;
import pl.olafcio.avoid_impl.net.fog.FogsNative;

public final class Fogs {
    @ApiStatus.Internal
    private Fogs() {}

    public static void register(Fog fog) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT)
            FogsNative.register(fog);
    }
}
