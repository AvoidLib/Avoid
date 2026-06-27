package pl.olafcio.avoid.net.entity_server.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity_type.EntityType;

@ApiStatus.Experimental
public record ServerEntityCreateEvent(EntityType entityType, Entity entity) {}
