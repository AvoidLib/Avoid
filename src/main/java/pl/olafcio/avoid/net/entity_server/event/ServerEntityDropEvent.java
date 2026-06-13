package pl.olafcio.avoid.net.entity_server.event;

import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.item.stack.ItemStack;

public final class ServerEntityDropEvent extends Cancellable {
    public final Entity entity;
    public final ItemStack stack;

    public ServerEntityDropEvent(Entity entity, ItemStack stack) {
        this.entity = entity;
        this.stack = stack;
    }
}
