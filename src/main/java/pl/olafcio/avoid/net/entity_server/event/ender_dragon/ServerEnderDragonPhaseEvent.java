package pl.olafcio.avoid.net.entity_server.event.ender_dragon;

import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.values.EnderDragonPhase;

/**
 * <b>Note:</b> Cancelling this event may break the ender dragon.
 */
public final class ServerEnderDragonPhaseEvent extends Cancellable {
    public final Entity dragon;
    public final EnderDragonPhase phase;

    public ServerEnderDragonPhaseEvent(Entity dragon, EnderDragonPhase phase) {
        this.dragon = dragon;
        this.phase = phase;
    }
}
