package pl.olafcio.avoid.net.screen.eventinterface;

import org.lwjgl.sdl.SDLKeycode;

public interface KeyInterface extends ModifierInterface {
    int getKey();

    /**
     * Checks if the input key is Enter, Numpad Enter or Space.
     */
    default boolean isEnterOrSpace() {
        return this.getKey() == SDLKeycode.SDLK_RETURN || this.getKey() == SDLKeycode.SDLK_SPACE || this.getKey() == SDLKeycode.SDLK_KP_ENTER;
    }

    /**
     * Checks if the input key is Enter.
     */
    default boolean isEnter() {
        return this.getKey() == SDLKeycode.SDLK_RETURN || this.getKey() == SDLKeycode.SDLK_KP_ENTER;
    }

    /**
     * Checks if the input key is Escape (Esc).
     */
    default boolean isEsc() {
        return this.getKey() == SDLKeycode.SDLK_ESCAPE;
    }

    /**
     * Checks if the input key is Arrow Left.
     */
    default boolean isArrowLeft() {
        return this.getKey() == SDLKeycode.SDLK_LEFT;
    }

    /**
     * Checks if the input key is Arrow Right.
     */
    default boolean isArrowRight() {
        return this.getKey() == SDLKeycode.SDLK_RIGHT;
    }

    /**
     * Checks if the input key is Arrow Up.
     */
    default boolean isArrowUp() {
        return this.getKey() == SDLKeycode.SDLK_UP;
    }

    /**
     * Checks if the input key is Arrow Down.
     */
    default boolean isArrowDown() {
        return this.getKey() == SDLKeycode.SDLK_DOWN;
    }

    /**
     * Checks if the input key is Tab.
     */
    default boolean isTab() {
        return this.getKey() == SDLKeycode.SDLK_TAB;
    }

    /**
     * Returns the calculated digit of the character.<br/>
     * For example, {@link SDLKeycode#SDLK_0} returns 0.
     */
    default int toDigit() {
        int i = this.getKey() - 48;
        return i >= 0 && i <= 9 ? i : -1;
    }

    /**
     * Checks if the input hotkey is ^A (Ctrl+A), which stands for Select All.
     */
    default boolean CtrlA() {
        return this.getKey() == SDLKeycode.SDLK_A && this.holdingControlSupport() && !this.holdingShift() && !this.holdingAlt();
    }

    /**
     * Checks if the input hotkey is ^C (Ctrl+C), which stands for Copy.
     */
    default boolean CtrlC() {
        return this.getKey() == SDLKeycode.SDLK_C && this.holdingControlSupport() && !this.holdingShift() && !this.holdingAlt();
    }

    /**
     * Checks if the input hotkey is ^V (Ctrl+V), which stands for Paste.
     */
    default boolean CtrlV() {
        return this.getKey() == SDLKeycode.SDLK_V && this.holdingControlSupport() && !this.holdingShift() && !this.holdingAlt();
    }

    /**
     * Checks if the input hotkey is ^X (Ctrl+X), which stands for Cut.
     */
    default boolean CtrlX() {
        return this.getKey() == SDLKeycode.SDLK_X && this.holdingControlSupport() && !this.holdingShift() && !this.holdingAlt();
    }
}
