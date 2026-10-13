package pl.olafcio.avoid.net.mouse.event;

import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.mouse.IMouseEvent;

public final class ClientMouseDownEvent extends Cancellable implements IMouseEvent {
    private final double x;
    private final double y;
    private final int button;
    private final int modifiers;

    public ClientMouseDownEvent(double x, double y, int button, int modifiers) {
        this.x = x;
        this.y = y;
        this.button = button;
        this.modifiers = modifiers;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public int getButton() {
        return button;
    }

    @Override
    public int getModifiers() {
        return modifiers;
    }
}
