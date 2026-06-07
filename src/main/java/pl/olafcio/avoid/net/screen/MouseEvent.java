package pl.olafcio.avoid.net.screen;

import net.minecraft.client.input.MouseButtonEvent;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.screen.eventinterface.ModifierInterface;

@ApiStatus.Experimental
public final class MouseEvent implements ModifierInterface {
    MouseButtonEvent event;

    MouseEvent() {}

    public double getX() {
        return event.x();
    }

    public double getY() {
        return event.y();
    }

    public int getButton() {
        return event.button();
    }

    public int getModifiers() {
        return event.modifiers();
    }
}
