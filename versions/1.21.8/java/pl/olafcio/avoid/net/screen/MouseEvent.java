package pl.olafcio.avoid.net.screen;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.screen.eventinterface.ModifierInterface;

@ApiStatus.Experimental
public final class MouseEvent implements ModifierInterface {
    double x;
    double y;
    int button;
    int modifiers;

    MouseEvent() {}

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getButton() {
        return button;
    }

    public int getModifiers() {
        return modifiers;
    }
}
