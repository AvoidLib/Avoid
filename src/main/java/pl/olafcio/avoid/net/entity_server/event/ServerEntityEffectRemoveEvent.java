package pl.olafcio.avoid.net.entity_server.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.mods.event_group.EntityEvent;
import pl.olafcio.avoid.net.effect.instance.EffectInstance;
import pl.olafcio.avoid.net.entity.Entity;

@ApiStatus.Experimental
public final class ServerEntityEffectRemoveEvent extends Cancellable implements EntityEvent {
    public final Entity entity;
    public final EffectInstance effect;

    public ServerEntityEffectRemoveEvent(Entity entity, EffectInstance effect) {
        this.entity = entity;
        this.effect = effect;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
