package pl.olafcio.avoid.mods.event_group;

import pl.olafcio.avoid.net.entity.Entity;

/**
 * An event occurring on an entity.
 */
public interface EntityEvent {
    Entity getEntity();
}
