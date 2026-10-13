package pl.olafcio.avoid.net.mouse;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.screen.eventinterface.ModifierInterface;

@ApiStatus.Experimental
public interface IMouseEvent extends ModifierInterface {
    double getX();
    double getY();

    int getButton();
    int getModifiers();
}
