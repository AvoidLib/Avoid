package pl.olafcio.avoid.net.entity_server.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.IncompatibleChange;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity_type.EntityType;

@IncompatibleChange(change = "No longer fired on the client",
                    reason = "This event was being accidentally also fired on the client. It, implicitly by name, should trigger only on the server.",
                    since = "v1.12")
@ApiStatus.Experimental
public record ServerEntityCreateEvent(EntityType entityType, Entity entity) {}
