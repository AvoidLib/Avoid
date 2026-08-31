package pl.olafcio.avoid.net.screen.widget.able;

public interface Focusable {
    /**
     * Sets whether the element is focused.
     */
    void setFocused(boolean value);

    /** Returns whether the element can be focused. */
    boolean isActive();

    /** Returns whether the element is focused. */
    boolean isFocused();

    /**
     * Returns whether the element should have {@code setFocused(true)} called on it<br/>
     * after the user interacts with it.
     */
    default boolean shouldFocusAfterInteraction() {
        return true;
    }
}
