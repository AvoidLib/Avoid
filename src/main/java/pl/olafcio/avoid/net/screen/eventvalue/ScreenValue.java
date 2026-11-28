package pl.olafcio.avoid.net.screen.eventvalue;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.screen.Screen;
import pl.olafcio.avoid.net.screen.ScreenMarker;

@ApiStatus.Experimental
public class ScreenValue {
    private Screen screen;

    public ScreenValue(Screen screen) {
        this.screen = screen;
    }

    protected ScreenValue() {}

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void setScreen(ScreenMarker screen) {
        this.screen = screen.create();
    }
}
