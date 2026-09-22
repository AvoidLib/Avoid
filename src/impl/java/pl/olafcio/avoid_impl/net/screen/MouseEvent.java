package pl.olafcio.avoid_impl.net.screen;

import net.minecraft.client.input.MouseButtonEvent;

public class MouseEvent extends pl.olafcio.avoid.net.screen.MouseEvent {
    MouseButtonEvent event;

    MouseEvent() {}

    @Override
    public double getX() {
        return event.x();
    }

    @Override
    public double getY() {
        return event.y();
    }

    @Override
    public int getButton() {
        return event.button();
    }

    @Override
    public int getModifiers() {
        return event.modifiers();
    }
}
