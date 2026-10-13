package pl.olafcio.avoid.net.mouse.event;

import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.mouse.IMouseEvent;
import pl.olafcio.avoid.net.screen.eventinterface.ModifierInterface;

public final class ClientMouseMoveEvent extends Cancellable implements ModifierInterface {
    private final double x;
    private final double y;
    private final int modifiers;

    public ClientMouseMoveEvent(double x, double y, int modifiers) {
        this.x = x;
        this.y = y;
        this.modifiers = modifiers;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    @Override
    public int getModifiers() {
        return modifiers;
    }
}
