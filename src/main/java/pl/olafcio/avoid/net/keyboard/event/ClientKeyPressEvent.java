package pl.olafcio.avoid.net.keyboard.event;

import net.minecraft.client.input.KeyEvent;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.screen.eventinterface.KeyInterface;
import pl.olafcio.avoid.net.screen.eventinterface.ModifierInterface;

@ApiStatus.Experimental
public final class ClientKeyPressEvent implements ModifierInterface, KeyInterface {
    KeyEvent event;

    ClientKeyPressEvent() {}

    public int getKey() {
        return event.key();
    }

    public int getScancode() {
        return event.scancode();
    }

    public int getModifiers() {
        return event.modifiers();
    }
}
