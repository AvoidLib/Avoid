package pl.olafcio.avoid.net.screen.event;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net.screen.*;

@NullMarked
public record ScreenInitEvent(
        Screen screen,
        int width,
        int height
) {
    @ApiStatus.Internal
    public ScreenInitEvent {}
}
