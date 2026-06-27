package pl.olafcio.avoid.net.gui.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.screen.Drawer;

@ApiStatus.Experimental
public final class RenderOutlineEvent extends Cancellable {
    private final Drawer drawer;

    public int x;
    public int y;

    public int width;
    public int height;

    public int color;

    public RenderOutlineEvent(Drawer drawer, int x, int y, int width, int height, int color) {
        this.drawer = drawer;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public Drawer getDrawer() {
        return drawer;
    }
}
