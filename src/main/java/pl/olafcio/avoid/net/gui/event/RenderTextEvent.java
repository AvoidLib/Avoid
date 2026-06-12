package pl.olafcio.avoid.net.gui.event;

import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.screen.Drawer;
import pl.olafcio.avoid.net.screen.font.Font;

public final class RenderTextEvent extends Cancellable {
    private final Drawer drawer;

    public Font font;
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
}
