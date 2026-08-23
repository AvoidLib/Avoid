package pl.olafcio.avoid.mods.event_group;

import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.player.Player;

public interface PlayerEvent extends EntityEvent {
    Player getPlayer();

    @Override
    default Entity getEntity() {
        return getPlayer();
    }
}
