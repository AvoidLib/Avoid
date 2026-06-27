package pl.olafcio.avoid.net.player_server.event.block.anvil.xp40;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.player.Player;

@ApiStatus.Experimental
public final class ServerPlayerAnvilXP40NameChangeCostClampEvent extends Cancellable {
    private final ItemStack itemStack;
    private final Player player;
    private final int cost;

    public ServerPlayerAnvilXP40NameChangeCostClampEvent(ItemStack itemStack, Player player, int cost) {
        this.itemStack = itemStack;
        this.player  = player;
        this.cost = cost;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCost() {
        return cost;
    }
}
