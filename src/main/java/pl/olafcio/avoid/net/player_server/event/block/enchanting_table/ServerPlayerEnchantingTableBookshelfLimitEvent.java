package pl.olafcio.avoid.net.player_server.event.block.enchanting_table;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;

@ApiStatus.Experimental
public final class ServerPlayerEnchantingTableBookshelfLimitEvent extends Cancellable {
    private int limit;

    public ServerPlayerEnchantingTableBookshelfLimitEvent(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
