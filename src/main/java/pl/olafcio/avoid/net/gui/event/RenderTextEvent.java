package pl.olafcio.avoid.net.gui.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.screen.Drawer;
import pl.olafcio.avoid.net.screen.font.Font;

@ApiStatus.Experimental
public final class RenderTextEvent extends Cancellable {
    private final Drawer drawer;

    @Deprecated(forRemoval = true)
    public Font font;

    @Deprecated(forRemoval = true)
    public final BaseComponent<?> component;

    public int x;
    public int y;

    public int color;
    public boolean shadow;

    public RenderTextEvent(Drawer drawer, Font font, BaseComponent<?> component, int x, int y, int color, boolean shadow) {
        this.drawer = drawer;
        this.font = font;
        this.component = component;
        this.x = x;
        this.y = y;
        this.color = color;
        this.shadow = shadow;
    }

    public Drawer getDrawer() {
        return drawer;
    }

    public Font getFont() {
        return font;
    }

    public BaseComponent<?> getComponent() {
        return component;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public boolean getShadow() {
        return shadow;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }
}
