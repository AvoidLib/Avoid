package pl.olafcio.avoid.net.keyboard.event;

import org.intellij.lang.annotations.MagicConstant;
import pl.olafcio.avoid.net.keyboard.Keyboard;
import pl.olafcio.avoid.net.screen.eventinterface.KeyInterface;
import pl.olafcio.avoid.net.screen.eventinterface.ModifierInterface;

public sealed abstract class ClientKeyEvent
                       implements ModifierInterface, KeyInterface
                       permits ClientKeyPressEvent, ClientKeyReleaseEvent
{
    int key;
    int scancode;
    int modifiers;

    ClientKeyEvent() {}

    @MagicConstant(valuesFromClass = Keyboard.class)
    public int getKey() {
        return key;
    }

    public int getScancode() {
        return scancode;
    }

    public int getModifiers() {
        return modifiers;
    }
}
