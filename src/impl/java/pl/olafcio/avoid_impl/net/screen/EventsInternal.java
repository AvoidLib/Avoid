package pl.olafcio.avoid_impl.net.screen;

import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.screen.KeyboardEvent;
import pl.olafcio.avoid.net.screen.MouseEvent;

@Native
@ApiStatus.Internal
public final class EventsInternal {
    private EventsInternal() {}

    public static pl.olafcio.avoid.net.screen.KeyboardEvent makeKeyboard() {
        return new pl.olafcio.avoid_impl.net.screen.KeyboardEvent();
    }

    public static pl.olafcio.avoid.net.screen.MouseEvent makeMouse() {
        return new pl.olafcio.avoid_impl.net.screen.MouseEvent();
    }

    public static void set(KeyboardEvent event, KeyEvent minecraft) {
        ((pl.olafcio.avoid_impl.net.screen.KeyboardEvent) event).event = minecraft;
    }

    public static void set(MouseEvent event, MouseButtonEvent minecraft) {
        ((pl.olafcio.avoid_impl.net.screen.MouseEvent) event).event = minecraft;  //at:mouse
    }
}
