package pl.olafcio.avoid.net.player_server.event.block.anvil;

import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.mods.event.UncancellableEventException;
import pl.olafcio.avoid.net.item.stack.ItemStack;

public final class ServerPlayerAnvilFailEvent extends Cancellable {
    private final Reason reason;

    private final ItemStack item1;
    private final ItemStack item2;

    public ServerPlayerAnvilFailEvent(Reason reason, ItemStack item1, ItemStack item2) {
        this.reason = reason;
        this.item1 = item1;
        this.item2 = item2;
    }

    public Reason getReason() {
        return reason;
    }

    public ItemStack getItem1() {
        return item1;
    }

    public ItemStack getItem2() {
        return item2;
    }

    @Override
    public void setCancelled(boolean value) {
        if (this.reason == Reason.EMPTY || this.reason == Reason.NOT_ENOUGH_DURABILITY_TO_REPAIR)
            throw new UncancellableEventException("Cannot cancel ServerPlayerAnvilFailEvent with #reason() == Reason.EMPTY || #reason() == Reason.NOT_ENOUGH_DURABILITY_TO_REPAIR");

        super.setCancelled(value);
    }

    public boolean isCancellable() {
        return !(this.reason == Reason.EMPTY || this.reason == Reason.NOT_ENOUGH_DURABILITY_TO_REPAIR);
    }

    public enum Reason {
        /** Triggered when the item in the first slot is air <b><u>(cannot be cancelled)</u></b>. */
        EMPTY,
        /** Triggered when the item in the first slot is unenchantable. */
        UNENCHANTABLE,
        /** Triggered when the item in the second slot is either fully damaged or the max damage of is smaller than 4 <b><u>(cannot be cancelled)</u></b>. */
        NOT_ENOUGH_DURABILITY_TO_REPAIR,
        /** Triggered when the item in the first slot has a different type than the item in the second slot <b><u>(cancelling not recommended)</u></b>. */
        NONBOOK_DIFFERENT,
        /** Triggered when the item in the second slot does not have stored enchants (usually means that it isn't an enchanted book)
         * and the item in the first slot is not damageable. */
        NONBOOK_UNDAMAGEABLE
    }
}
