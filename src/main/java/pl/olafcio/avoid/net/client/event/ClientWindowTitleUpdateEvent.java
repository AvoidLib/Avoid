package pl.olafcio.avoid.net.client.event;

import org.jetbrains.annotations.ApiStatus;

/**
 * Fired when the game recreates the window title.<br/>
 * This doesn't have to mean the title will change, however it may.
 */
@ApiStatus.Experimental
public final class ClientWindowTitleUpdateEvent {
    private String title;

    public ClientWindowTitleUpdateEvent(String title) {
        this.title = title;
    }

    /**
     * Returns the title the game tried to change to.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title the game will change to.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
