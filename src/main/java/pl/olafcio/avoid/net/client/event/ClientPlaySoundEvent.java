package pl.olafcio.avoid.net.client.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.sound.ClientSound;

/**
 * Fired when a sound should be played on the client.
 */
@ApiStatus.Experimental
public final class ClientPlaySoundEvent extends Cancellable {
    private final ClientSound sound;

    public ClientPlaySoundEvent(ClientSound sound) {
        this.sound = sound;
    }

    public ClientSound sound() {
        return sound;
    }
}
