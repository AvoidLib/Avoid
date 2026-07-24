package pl.olafcio.avoid.net.entity.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.IncompatibleChange;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity_type.EntityType;

@ApiStatus.Experimental
public record ClientEntityCreateEvent(EntityType entityType, Entity entity) {}
