package pl.olafcio.avoid.net.screen.eventinterface;

public interface KeyInterface extends ModifierInterface {
    int getKey();

    /**
     * Checks if the input key is Enter, Numpad Enter or Space.
     */
    default boolean isEnterOrSpace() {
        throw new UnsupportedOperationException("[KeyInterface#isEnterOrSpace] Cannot be ran on Velocity");
    }

    /**
     * Checks if the input key is Enter.
     */
    default boolean isEnter() {
        throw new UnsupportedOperationException("[KeyInterface#isEnterOrSpace] Cannot be ran on Velocity");
    }

    /**
     * Checks if the input key is Escape (Esc).
     */
    default boolean isEsc() {
        throw new UnsupportedOperationException("[KeyInterface#isEnterOrSpace] Cannot be ran on Velocity");
    }

    /**
     * Checks if the input key is Arrow Left.
     */
    default boolean isArrowLeft() {
        throw new UnsupportedOperationException("[KeyInterface#isEnterOrSpace] Cannot be ran on Velocity");
    }

    /**
     * Checks if the input key is Arrow Right.
     */
    default boolean isArrowRight() {
        throw new UnsupportedOperationException("[KeyInterface#isEnterOrSpace] Cannot be ran on Velocity");
    }

    /**
     * Checks if the input key is Arrow Up.
     */
    default boolean isArrowUp() {
        throw new UnsupportedOperationException("[KeyInterface#isEnterOrSpace] Cannot be ran on Velocity");
    }

    /**
     * Checks if the input key is Arrow Down.
     */
    default boolean isArrowDown() {
        throw new UnsupportedOperationException("[KeyInterface#isEnterOrSpace] Cannot be ran on Velocity");
    }

    /**
     * Checks if the input key is Tab.
     */
    default boolean isTab() {
        throw new UnsupportedOperationException("[KeyInterface#isEnterOrSpace] Cannot be ran on Velocity");
    }

    /**
     * Returns the calculated digit of the character.<br/>
     * For example, {@link GLFW#GLFW_KEY_0} returns 0.
     */
    default int toDigit() {
        throw new UnsupportedOperationException("[KeyInterface#isEnterOrSpace] Cannot be ran on Velocity");
    }

    /**
     * Checks if the input hotkey is ^A (Ctrl+A), which stands for Select All.
     */
    default boolean CtrlA() {
        throw new UnsupportedOperationException("[KeyInterface#isEnterOrSpace] Cannot be ran on Velocity");
    }

    /**
     * Checks if the input hotkey is ^C (Ctrl+C), which stands for Copy.
     */
    default boolean CtrlC() {
        throw new UnsupportedOperationException("[KeyInterface#isEnterOrSpace] Cannot be ran on Velocity");
    }

    /**
     * Checks if the input hotkey is ^V (Ctrl+V), which stands for Paste.
     */
    default boolean CtrlV() {
        throw new UnsupportedOperationException("[KeyInterface#isEnterOrSpace] Cannot be ran on Velocity");
    }

    /**
     * Checks if the input hotkey is ^X (Ctrl+X), which stands for Cut.
     */
    default boolean CtrlX() {
        throw new UnsupportedOperationException("[KeyInterface#isEnterOrSpace] Cannot be ran on Velocity");
    }
}
