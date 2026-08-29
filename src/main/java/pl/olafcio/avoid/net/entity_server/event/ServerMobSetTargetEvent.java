package pl.olafcio.avoid.net.entity_server.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.entity.Entity;

@ApiStatus.Experimental
public final class ServerMobSetTargetEvent extends Cancellable {
    private final Entity target;
    private final Entity mob;

    public ServerMobSetTargetEvent(Entity target, Entity mob) {
        this.target = target;
        this.mob = mob;
    }

    public Entity getTarget() {
        return target;
    }

    public Entity getMob() {
        return mob;
    }
}
