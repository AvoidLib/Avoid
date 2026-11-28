package pl.olafcio.avoid_impl.net.screen;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid_impl.mixininterface.IScreen;

@Native
@ApiStatus.Internal
public class NativeScreenNative {
    @ApiStatus.Internal
    private NativeScreenNative() {}

    public static NativeScreen create(IScreen screen) {
        return new NativeScreen(screen);
    }
}
