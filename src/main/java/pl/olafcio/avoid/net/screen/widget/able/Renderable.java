package pl.olafcio.avoid.net.screen.widget.able;

import pl.olafcio.avoid.net.screen.Drawer;

public interface Renderable {
    /**
     * Renders the element.
     */
    void render(Drawer ctx, int mouseX, int mouseY, float deltaTick);
}
