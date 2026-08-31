package pl.olafcio.avoid.net.screen;

import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class EventsInternal {
    private EventsInternal() {}

    public static KeyboardEvent makeKeyboard() {
        return new KeyboardEvent();
    }

    public static MouseEvent makeMouse() {
        return new MouseEvent();
    }

    public static void set(KeyboardEvent event, KeyEvent minecraft) {
        event.event = minecraft;
    }

    public static void set(MouseEvent event, MouseButtonEvent minecraft) {
        event.event = minecraft;  //at:mouse
    }
}
