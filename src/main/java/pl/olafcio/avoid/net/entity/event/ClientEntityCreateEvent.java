package pl.olafcio.avoid.net.entity.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.IncompatibleChange;
import pl.olafcio.avoid.mods.event_group.EntityEvent;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity_type.EntityType;

@ApiStatus.Experimental
public record ClientEntityCreateEvent(EntityType entityType, Entity entity) implements EntityEvent {
    @Override
    public Entity getEntity() {
        return null;
    }
}
