package pl.olafcio.avoid.net.screen.widget.able;

import pl.olafcio.avoid.net.screen.KeyboardEvent;
import pl.olafcio.avoid.net.screen.MouseEvent;

public interface Attachable extends Focusable {
    default void onMouseMoved(double x, double y) {
    }

    default boolean onMousePressed(MouseEvent mouseEvent, boolean doubleClick) {
        return false;
    }

    default boolean onMouseReleased(MouseEvent mouseEvent) {
        return false;
    }

    default boolean onMouseDragged(MouseEvent mouseEvent, double d, double e) {
        return false;
    }

    default boolean onMouseScrolled(double d, double e, double f, double g) {
        return false;
    }

    default boolean onKeyPressed(KeyboardEvent keyEvent) {
        return false;
    }

    default boolean onKeyReleased(KeyboardEvent keyEvent) {
        return false;
    }

//    default boolean onCharTyped(CharacterEvent characterEvent) {
//        return false;
//    }
}
