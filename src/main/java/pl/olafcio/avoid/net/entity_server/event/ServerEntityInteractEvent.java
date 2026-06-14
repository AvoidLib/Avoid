package pl.olafcio.avoid.net.entity_server.event;

import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.player.Player;

public final class ServerEntityInteractEvent extends Cancellable {
    public final Entity victim;
    public final Player attacker;

    public ServerEntityInteractEvent(Entity victim, Player attacker) {
        this.victim = victim;
        this.attacker = attacker;
    }
}
