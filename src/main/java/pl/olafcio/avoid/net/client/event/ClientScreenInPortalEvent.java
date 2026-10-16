package pl.olafcio.avoid.net.client.event;

import pl.olafcio.avoid.net.screen.Screen;

import java.util.Objects;

/**
 * Fired when a screen is open and the player is inside a portal.
 */
public final class ClientScreenInPortalEvent {
    private final Screen screen;
    private boolean allowed;

    public ClientScreenInPortalEvent(Screen screen, boolean allowed) {
        this.screen = screen;
        this.allowed = allowed;
    }

    /**
     * Returns the screen that the game is considering to close.
     */
    public Screen screen() {
        return screen;
    }

    /**
     * Returns whether the game wants to close this {@link #screen()}.
     */
    public boolean allowed() {
        return allowed;
    }

    /**
     * Sets whether the game will close this {@link #screen()}.
     */
    public void setAllowed(boolean value) {
        this.allowed = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        var that = (ClientScreenInPortalEvent) obj;

        return Objects.equals(this.screen, that.screen) &&
                this.allowed == that.allowed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(screen, allowed);
    }

    @Override
    public String toString() {
        return "ClientScreenInPortalEvent[" +
                "screen=" + screen + ", " +
                "allowed=" + allowed + ']';
    }

}
