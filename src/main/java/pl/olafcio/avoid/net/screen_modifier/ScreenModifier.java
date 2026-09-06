package pl.olafcio.avoid.net.screen_modifier;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.screen.*;
import pl.olafcio.avoid.net.screen.widget.able.Renderable;

import java.util.Objects;

@ApiStatus.Experimental
public abstract class ScreenModifier {
    Screen screen;

    boolean cancel = true;
    boolean skip = false;

    String   functionName;
    Function function;

    @SuppressWarnings("unchecked")
    private <T> T invoke(String name, Object... args) {
        if (!Objects.equals(functionName, name))
            throw new OffContextCallException("Cannot invoke '%s' in off-context".formatted(name));

        return (T) function.invoke(args);
    }

    public Screen getScreen() {
        return screen;
    }

    @Nullable
    public Renderable widget(WidgetMarker marker) {
        return screen.widget(marker);
    }

    //============//
    // INJECTIONS //
    //============//

    @ApiStatus.OverrideOnly
    public void init() {
        cancel = false;

        if (skip)
            skip = false;
        else
            invoke("init");
    }

    @ApiStatus.OverrideOnly
    public void render(Drawer gui, int mouseX, int mouseY, float tickDelta) {
        cancel = false;

        if (skip)
            skip = false;
        else
            invoke("render", gui, mouseX, mouseY, tickDelta);
    }

    //TODO: Make mouse & key injectors
    //      They are harder to do because I'm not sure if I will do an @Override in the mixin then won't I accidentally
    //           override custom screen implementations

//    public boolean mouseClicked(MouseEvent event, boolean doubleClick) {
//        cancel = false;
//        return screen.mouseClicked(event, doubleClick);
//    }
//
//    public boolean mouseReleased(MouseEvent event) {
//        cancel = false;
//        return screen.mouseReleased(event);
//    }
//
//    public boolean mouseScrolled(double x, double y, double scrolledX, double scrolledY) {
//        cancel = false;
//        return screen.mouseScrolled(x, y, scrolledX, scrolledY);
//    }
//
//    public boolean mouseDragged(MouseEvent event, double x, double y) {
//        cancel = false;
//        return screen.mouseDragged(event, x, y);
//    }
//
//    public boolean keyPressed(KeyboardEvent event) {
//        cancel = false;
//        return screen.keyPressed(event);
//    }
//
//    public boolean keyReleased(KeyboardEvent event) {
//        cancel = false;
//        return screen.keyReleased(event);
//    }
}
