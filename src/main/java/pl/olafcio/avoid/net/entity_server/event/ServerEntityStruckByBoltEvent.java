package pl.olafcio.avoid.net.entity_server.event;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.mods.event_group.EntityEvent;
import pl.olafcio.avoid.net.entity.Entity;

@NeverRemoval
public final class ServerEntityStruckByBoltEvent extends Cancellable implements EntityEvent {
    public final Entity entity;
    public final Entity bolt;

    public ServerEntityStruckByBoltEvent(Entity entity, Entity bolt) {
        this.entity = entity;
        this.bolt = bolt;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
