package pl.olafcio.avoid.net.server.event;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.sound.Sound;

@NullMarked
public final class ServerPlaySoundEvent extends Cancellable {
    private final Sound sound;
    private final @Nullable Entity entity;

    public ServerPlaySoundEvent(Sound sound, @Nullable Entity entity) {
        this.sound = sound;
        this.entity = entity;
    }

    public Sound sound() {
        return sound;
    }

    public @Nullable Entity origin() {
        return entity;
    }
}
