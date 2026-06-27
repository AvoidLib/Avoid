package pl.olafcio.avoid.net.player_server.event.block.enchanting_table;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.world.World;

@ApiStatus.Experimental
public final class ServerPlayerEnchantingTableBookshelfAmountEvent extends Cancellable {
    private int amount;

    private final World world;
    private final BlockPos blockPos;

    public ServerPlayerEnchantingTableBookshelfAmountEvent(int amount, World world, BlockPos blockPos) {
        this.amount = amount;
        this.world = world;
        this.blockPos = blockPos;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}
