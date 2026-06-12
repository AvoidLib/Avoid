package pl.olafcio.avoid.net.screen.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.mixininterface.IScreen;

@Native
@ApiStatus.Internal
public final class ScreenOpenEventNative {
    @ApiStatus.Internal
    private ScreenOpenEventNative() {}

    public static IScreen getScreen(ScreenOpenEvent event) {
        return event.screen;
    }
}
