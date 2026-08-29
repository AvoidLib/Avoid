package pl.olafcio.avoid.net.entity_server.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.entity.Entity;

@ApiStatus.Experimental
public final class ServerMobClearTargetEvent extends Cancellable {
    private final Entity mob;

    public ServerMobClearTargetEvent(Entity mob) {
        this.mob = mob;
    }

    public Entity getMob() {
        return mob;
    }
}
