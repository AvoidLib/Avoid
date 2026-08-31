package pl.olafcio.avoid.net.screen_modifier;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.RunningEnv;

import java.util.function.Supplier;

@ApiStatus.Experimental
public final class ScreenModifiers {
    @ApiStatus.Internal
    private ScreenModifiers() {}

    public static void register(Supplier<ScreenModifier> supplier) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT)
            ScreenModifiersNative.register(supplier);
    }
}
