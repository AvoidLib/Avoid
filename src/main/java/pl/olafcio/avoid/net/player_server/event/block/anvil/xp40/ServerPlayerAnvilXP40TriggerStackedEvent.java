package pl.olafcio.avoid.net.player_server.event.block.anvil.xp40;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.player.Player;

@ApiStatus.Experimental
public final class ServerPlayerAnvilXP40TriggerStackedEvent extends Cancellable {
    private final ItemStack item;
    private final ItemStack item2;

    private final Player player;

    public ServerPlayerAnvilXP40TriggerStackedEvent(ItemStack item, ItemStack item2, Player player) {
        this.item = item;
        this.item2 = item2;
        this.player = player;
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemStack getItem2() {
        return item2;
    }

    public Player getPlayer() {
        return player;
    }
}
