package pl.olafcio.avoid.net.screen;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.screen.eventinterface.ModifierInterface;

@ApiStatus.Experimental
@ApiStatus.NonExtendable
public abstract class MouseEvent implements ModifierInterface {
    public abstract double getX();
    public abstract double getY();

    public abstract int getButton();
    public abstract int getModifiers();
}
