package pl.olafcio.avoid.net.entity.event;

import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.mods.event.UncancellableEventException;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.world.vect3.IVect3;

public final class ClientEntityVelocityEvent extends Cancellable {
    private final Entity entity;
    private IVect3 velocity;

    private boolean veloChanged = false;

    private final boolean cancellable;

    public ClientEntityVelocityEvent(Entity entity, IVect3 velocity, boolean cancellable) {
        this.entity = entity;
        this.velocity = velocity;
        this.cancellable = cancellable;
    }

    @Override
    public void setCancelled(boolean value) {
        if (!cancellable)
            throw new UncancellableEventException("Cancelling velocity events of remote entities from the client is not permitted");

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
