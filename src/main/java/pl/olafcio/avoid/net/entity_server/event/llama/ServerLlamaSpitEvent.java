package pl.olafcio.avoid.net.entity_server.event.llama;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.entity.Entity;

@ApiStatus.Experimental
public final class ServerLlamaSpitEvent extends Cancellable {
    public final Entity victim;
    public final Entity attacker;

    public ServerLlamaSpitEvent(Entity victim, Entity attacker) {
        this.victim = victim;
        this.attacker = attacker;
    }
}
