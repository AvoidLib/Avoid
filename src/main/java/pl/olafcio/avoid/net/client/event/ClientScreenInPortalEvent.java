package pl.olafcio.avoid.net.client.event;

import pl.olafcio.avoid.net.screen.Screen;

import java.util.Objects;

public final class ClientScreenInPortalEvent {
    private final Screen screen;
    private boolean allowed;

    public ClientScreenInPortalEvent(Screen screen, boolean allowed) {
        this.screen = screen;
        this.allowed = allowed;
    }

    public Screen screen() {
        return screen;
    }

    public boolean allowed() {
        return allowed;
    }

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
