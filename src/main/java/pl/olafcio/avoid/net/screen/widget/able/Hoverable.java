package pl.olafcio.avoid.net.screen.widget.able;

public interface Hoverable {
    /**
     * Returns whether the element is hovered.
     */
    boolean isHovered();

    default boolean isHovered(double mouseX, double mouseY) {
        return false;
    }
}
