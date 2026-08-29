package pl.olafcio.avoid.net.entity_server.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.mods.event_group.EntityEvent;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.values.Damage;

@ApiStatus.Experimental
public final class ServerEntityResurrectEvent extends Cancellable implements EntityEvent {
    private final Entity entity;
    private final Damage damage;

    public ServerEntityResurrectEvent(Entity entity, Damage damage) {
        this.entity = entity;
        this.damage = damage;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    public Damage getDamage() {
        return damage;
    }
}
