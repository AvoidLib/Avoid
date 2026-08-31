package pl.olafcio.avoid.net.screen_modifier;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.screen.Drawer;
import pl.olafcio.avoid.net.screen.KeyboardEvent;
import pl.olafcio.avoid.net.screen.MouseEvent;
import pl.olafcio.avoid.net.screen.Screen;

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

    //============//
    // INJECTIONS //
    //============//

    public void init() {
        cancel = false;

        if (!skip)
            invoke("init");
    }

    public void render(Drawer gui, int mouseX, int mouseY, float tickDelta) {
        cancel = false;

        if (!skip)
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
