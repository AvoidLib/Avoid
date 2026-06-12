package pl.olafcio.avoid.net.screen;

import pl.olafcio.avoid.mixininterface.IScreen;
import pl.olafcio.avoid.net.chat.converter.COFromNative;

public final class NativeScreen extends Screen {
    public final net.minecraft.client.gui.screens.Screen realScreen;

    NativeScreen(IScreen castScreen) {
        super(COFromNative.from(((net.minecraft.client.gui.screens.Screen) castScreen).getTitle()));

        this.realScreen = (net.minecraft.client.gui.screens.Screen) castScreen;
        this.realScreen.added();
    }

    @Override
    public void init() {
        realScreen.width = width;
        realScreen.height = height;

        realScreen.init();
    }

    @Override
    protected void resize(int width, int height) {
        realScreen.resize(width, height);
    }

    @Override
    public void render(Drawer gui, int mouseX, int mouseY, float tickDelta) {
        realScreen.render(gui.graphics, mouseX, mouseY, tickDelta);
    }

    @Override
    public void mouseMoved(double x, double y) {
        realScreen.mouseMoved(x, y);
    }

    @Override
    public boolean mouseClicked(MouseEvent event, boolean doubleClick) {
        return realScreen.mouseClicked(event.event, doubleClick);
    }

    @Override
    public boolean mouseReleased(MouseEvent event) {
        return realScreen.mouseReleased(event.event);
    }

    @Override
    public boolean mouseScrolled(double x, double y, double scrolledX, double scrolledY) {
        return realScreen.mouseScrolled(x, y, scrolledX, scrolledY);
    }

    @Override
    public boolean mouseDragged(MouseEvent event, double x, double y) {
        return realScreen.mouseDragged(event.event, x, y);
    }

    @Override
    public boolean keyPressed(KeyboardEvent event) {
        return realScreen.keyPressed(event.event);
    }

    @Override
    public boolean keyReleased(KeyboardEvent event) {
        return realScreen.keyReleased(event.event);
    }

    @Override
    protected boolean isMouseWithin(double x1, double y1, double x2, double y2) {
        return false;
    }

    @Override
    protected boolean isMouseOver(double x, double y) {
        return realScreen.isMouseOver(x, y);
    }
}
