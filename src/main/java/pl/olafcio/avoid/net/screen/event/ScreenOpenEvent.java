package pl.olafcio.avoid.net.screen.event;

import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.mixininterface.IScreen;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.screen.*;

@NullMarked
@WillRefactor(aspect = "'null' acceptance")
public final class ScreenOpenEvent extends Cancellable {
    IScreen screen;

    public ScreenOpenEvent(IScreen screen) {
        this.screen = screen;
    }

    public pl.olafcio.avoid.net.screen.Screen getScreen() {
        return screen instanceof AvoidScreen av
                ? av.screen
                : NativeScreenNative.create(screen);
    }

    public void setScreen(Screen screen) {
        this.screen = (IScreen) new AvoidScreen(screen);
    }

    public void setScreen(ScreenMarker screen) {
        this.screen = (IScreen) ((NativeScreen) screen.create()).realScreen;
    }
}
