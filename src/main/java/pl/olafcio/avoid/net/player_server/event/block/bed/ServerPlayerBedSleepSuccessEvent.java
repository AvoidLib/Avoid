package pl.olafcio.avoid.net.player_server.event.block.bed;

import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.world.World;

public final class ServerPlayerBedSleepSuccessEvent extends Cancellable {
    private final Player player;
    private final World world;
    private final BlockPos blockPos;

    public ServerPlayerBedSleepSuccessEvent(Player player, World world, BlockPos blockPos) {
        this.player = player;
        this.world = world;
        this.blockPos = blockPos;
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}
