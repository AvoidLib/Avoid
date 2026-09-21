package pl.olafcio.avoid.net.screen;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.Discouraged;

@Discouraged(reason = "may be revamped")
@ApiStatus.Experimental
public final class ScreenAccessor {
    @ApiStatus.Internal
    private ScreenAccessor() {}

    public static void setWidth(Screen screen, int width) {
        screen.width = width;
    }

    public static void setHeight(Screen screen, int height) {
        screen.height = height;
    }

    public static void resize(Screen screen, int width, int height) {
        screen.resize(width, height);
    }

    public static boolean isMouseOver(Screen screen, double x, double y) {
        return screen.isMouseOver(x, y);
    }
}
