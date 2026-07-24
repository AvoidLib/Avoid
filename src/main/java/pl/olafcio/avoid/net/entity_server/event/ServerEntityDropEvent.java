package pl.olafcio.avoid.net.entity_server.event;

import pl.olafcio.avoid.annotations.refactor.IncompatibleChange;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.item.stack.ItemStack;

@IncompatibleChange(change = "No longer fired on the client",
                    reason = "This event might've been getting accidentally also fired on the client. It, implicitly by name, should trigger only on the server.",
                    since = "v1.12")
@NeverRemoval
public final class ServerEntityDropEvent extends Cancellable {
    public final Entity entity;
    public final ItemStack stack;

    public ServerEntityDropEvent(Entity entity, ItemStack stack) {
        this.entity = entity;
        this.stack = stack;
    }
}
