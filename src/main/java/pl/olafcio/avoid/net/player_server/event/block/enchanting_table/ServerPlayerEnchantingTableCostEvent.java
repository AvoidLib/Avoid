package pl.olafcio.avoid.net.player_server.event.block.enchanting_table;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public final class ServerPlayerEnchantingTableCostEvent {
    private int cost;

    public ServerPlayerEnchantingTableCostEvent(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
