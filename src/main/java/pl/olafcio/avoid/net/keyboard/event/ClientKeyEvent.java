package pl.olafcio.avoid.net.keyboard.event;

import net.minecraft.client.input.KeyEvent;
import pl.olafcio.avoid.net.screen.eventinterface.KeyInterface;
import pl.olafcio.avoid.net.screen.eventinterface.ModifierInterface;

public sealed abstract class ClientKeyEvent
                       implements ModifierInterface, KeyInterface
                       permits ClientKeyPressEvent, ClientKeyReleaseEvent
{
    KeyEvent event;

    ClientKeyEvent() {}

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
