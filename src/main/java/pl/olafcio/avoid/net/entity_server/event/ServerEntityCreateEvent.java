package pl.olafcio.avoid.net.entity_server.event;

import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity_type.EntityType;

public record ServerEntityCreateEvent(EntityType entityType, Entity entity) {}
