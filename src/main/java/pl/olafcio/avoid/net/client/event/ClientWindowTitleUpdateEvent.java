package pl.olafcio.avoid.net.client.event;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public final class ClientWindowTitleUpdateEvent {
    private String title;

    public ClientWindowTitleUpdateEvent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
