package pl.olafcio.avoid.net.screen.event;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.annotations.refactor.Discouraged;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.screen.eventvalue.ScreenValue;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.screen.*;

@NullMarked
@WillRefactor(aspect = "'null' acceptance")
public final class ScreenOpenEvent extends Cancellable {
    private final ScreenValue value;

    public ScreenOpenEvent(ScreenValue value) {
        this.value = value;
    }

    public pl.olafcio.avoid.net.screen.Screen getScreen() {
        return value.getScreen();
    }

    public void setScreen(Screen screen) {
        value.setScreen(screen);
    }

    public void setScreen(ScreenMarker screen) {
        value.setScreen(screen);
    }

    @Discouraged(reason = "Meant to be used internally for optimization.")
    @ApiStatus.Experimental
    public ScreenValue getValue() {
        return value;
    }
}
