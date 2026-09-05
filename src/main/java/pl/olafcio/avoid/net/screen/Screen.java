package pl.olafcio.avoid.net.screen;

import com.google.common.base.CaseFormat;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.mods.loader.AvoidModLoader;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.screen.widget.able.Renderable;
import pl.olafcio.avoid.net.screen.widget.container.Container;

import java.net.MalformedURLException;

/**
 * An abstract screen class.
 * <br/>
 * A screen is a view for the whole Minecraft window.
 * Its goal is <u>rendering</u> and <u>handling mouse and keyboard</u> events.
 */
@NeverRemoval
public abstract class Screen extends Container implements MarkedScreen {
    final BaseComponent<?> accessibilityTitle;

    protected double lastMouseX = -1;
    protected double lastMouseY = -1;

    protected int width;
    protected int height;

    public Screen(BaseComponent<?> accessibilityTitle) {
        this.accessibilityTitle = accessibilityTitle;
    }

    public final BaseComponent<?> getAccessibilityTitle() {
        return accessibilityTitle;
    }

    /**
     * Called when the screen is added, and unless you override the {@link Screen#resize} method, resized.
     */
    public void init() {}

    /**
     * Called when the screen should render.
     */
    public void render(Drawer gui, int mouseX, int mouseY, float tickDelta) {
    }

    /**
     * Called when the screen is resized.<br/>
     * This method is not responsible for setting the {@link Screen#width} and {@link Screen#height} fields.
     * It is done by internal AvoidLib code automatically.
     */
    protected void resize(int width, int height) {
        this.init();
    }

    /**
     * Called when the user moves the mouse.
     */
    public void mouseMoved(double x, double y) {
        lastMouseX = x;
        lastMouseY = y;
    }

    /**
     * Called when the user presses a mouse button.
     * <h2>Don't run asynchronous tasks here!</h2>
     * If you do, copy over the MouseEvent object.
     * It's not new every call, and it's shared with other events!
     */
    public boolean mouseClicked(MouseEvent event, boolean doubleClick) {
        return false;
    }

    /**
     * Called when the user releases a mouse button.
     * <h2>Don't run asynchronous tasks here!</h2>
     * If you do, copy over the MouseEvent object.
     * It's not new every call, and it's shared with other events!
     */
    public boolean mouseReleased(MouseEvent event) {
        return false;
    }

    /**
     * Called when the user uses the mouse scroll, either vertically or horizontally.
     */
    public boolean mouseScrolled(double x, double y, double scrolledX, double scrolledY) {
        return false;
    }

    /**
     * Called when the user presses the mouse, changes its position and releases it.
     * <h2>Don't run asynchronous tasks here!</h2>
     * If you do, copy over the MouseEvent object.
     * It's not new every call, and it's shared with other events!
     */
    public boolean mouseDragged(MouseEvent event, double x, double y) {
        return false;
    }

    /**
     * Called when the user presses a keyboard key.
     * <h2>Don't run asynchronous tasks here!</h2>
     * If you do, copy over the KeyboardEvent object.
     * It's not new every call, and it's shared with other events!
     */
    public boolean keyPressed(KeyboardEvent event) {
        return false;
    }

    /**
     * Called when the user presses a keyboard key.
     * <h2>Don't run asynchronous tasks here!</h2>
     * If you do, copy over the KeyboardEvent object.
     * It's not new every call, and it's shared with other events!
     */
    public boolean keyReleased(KeyboardEvent event) {
        return false;
    }

    protected boolean isMouseWithin(double x1, double y1, double x2, double y2) {
        return lastMouseX >= x1 && lastMouseY >= y1 &&
               lastMouseX <= x2 && lastMouseY <= y2;
    }

    protected boolean isMouseOver(double x, double y) {
        return true;
    }

    //===========//
    // CONTAINER //
    //===========//

    /**
     * @apiNote This doesn't work on native (non-avoid) screens!
     */
    @Override
    @ApiStatus.Experimental
    public void insert(Renderable widget, int index) {
        super.insert(widget, index);
    }

    @Override
    @Nullable
    @ApiStatus.Experimental
    public Renderable widget(WidgetMarker marker) {
        return null;
    }

    //====//
    // IS //
    //====//

    public boolean is(Identification id) {
        final var klass = this.getClass();
        final var klassName = CaseFormat.UPPER_CAMEL.to(
                                                            CaseFormat.LOWER_UNDERSCORE,
                                                            klass.getName()
                                                                 .replaceAll("(\\..*)(Screen|(\\$.*)Screen)", "$1$3")
                                                                 .replace(".", "/")
                                                       );

        for (var addon : AvoidModLoader.getLoadedAddons()) {
            try {
                if (AvoidModLoader.getLoadedAddonFile(addon).toUri().toURL().equals(klass.getProtectionDomain().getCodeSource().getLocation())) {
                    if (!addon.id().equalsIgnoreCase(id.namespace()))
                        return false;

                    if (!klassName.toLowerCase().endsWith(id.path().toLowerCase()))
                        return false;

                    return true;
                }
            } catch (MalformedURLException ignored) {
            }
        }

        return false;
    }
}
