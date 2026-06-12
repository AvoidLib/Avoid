package pl.olafcio.avoid.net.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net.chat.converter.COToNative;

@NullMarked
@ApiStatus.Internal
public class AvoidScreen extends Screen {
    public final pl.olafcio.avoid.net.screen.Screen screen;

    private final Drawer drawer;
    private final MouseEvent mouse;
    private final KeyboardEvent keyboard;

    public AvoidScreen(pl.olafcio.avoid.net.screen.Screen screen) {
        super(COToNative.from(screen.accessibilityTitle));

        this.screen = screen;
        this.drawer = new Drawer();
        this.mouse = new MouseEvent();
        this.keyboard = new KeyboardEvent();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        drawer.graphics = guiGraphics;
        screen.render(drawer, mouseX, mouseY, tickDelta);
    }

    @Override
    public void init() {
        super.init();

        screen.width = width;
        screen.height = height;

        screen.init();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        screen.resize(width, height);
    }

    @Override
    public void mouseMoved(double x, double y) {
        screen.mouseMoved(x, y);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean doubleClick) {
        mouse.event = mouseButtonEvent;

        return screen.mouseClicked(mouse, doubleClick);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent mouseButtonEvent) {
        mouse.event = mouseButtonEvent;

        return screen.mouseReleased(mouse);
    }

    @Override
    public boolean mouseScrolled(double x, double y, double scrolledX, double scrolledY) {
        return screen.mouseScrolled(x, y, scrolledX, scrolledY);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent mouseButtonEvent, double x, double y) {
        mouse.event = mouseButtonEvent;

        return screen.mouseDragged(mouse, x, y);
    }

    @Override
    public boolean isMouseOver(double x, double y) {
        return screen.isMouseOver(x, y);
    }

    @Override
    public boolean keyPressed(KeyEvent keyEvent) {
        keyboard.event = keyEvent;

        return screen.keyPressed(keyboard);
    }

    @Override
    public boolean keyReleased(KeyEvent keyEvent) {
        keyboard.event = keyEvent;

        return screen.keyReleased(keyboard);
    }
}
