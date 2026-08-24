package pl.olafcio.avoid.net.fog;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.RunningEnv;

public final class Fogs {
    @ApiStatus.Internal
    private Fogs() {}

    public static void register(Fog fog) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT)
            FogsNative.register(fog);
    }
}
