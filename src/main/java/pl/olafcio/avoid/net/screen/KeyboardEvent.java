package pl.olafcio.avoid.net.screen;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.screen.eventinterface.KeyInterface;
import pl.olafcio.avoid.net.screen.eventinterface.ModifierInterface;

@ApiStatus.Experimental
@ApiStatus.NonExtendable
public abstract class KeyboardEvent implements ModifierInterface, KeyInterface {
    public abstract int getKey();
    public abstract int getScancode();
    public abstract int getModifiers();
}
