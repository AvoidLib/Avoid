package pl.olafcio.avoid.net.entity_server.event;

import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.mods.event.UncancellableEventException;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.world.vect3.IVect3;

public final class ServerEntityVelocityEvent extends Cancellable {
    private final Entity entity;
    private IVect3 velocity;

    private boolean veloChanged = false;

    public ServerEntityVelocityEvent(Entity entity, IVect3 velocity) {
        this.entity = entity;
        this.velocity = velocity;
    }

    @Override
    public void setCancelled(boolean value) {
        super.setCancelled(value);
    }

    public Entity getEntity() {
        return entity;
    }

    public IVect3 getVelocity() {
        return velocity;
    }

    public void setVelocity(IVect3 velocity) {
        this.velocity = velocity;
        this.veloChanged = true;
    }

    public boolean isVelocityChanged() {
        return veloChanged;
    }
}
