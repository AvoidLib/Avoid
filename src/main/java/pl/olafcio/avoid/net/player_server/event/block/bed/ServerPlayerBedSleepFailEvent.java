package pl.olafcio.avoid.net.player_server.event.block.bed;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.world.World;

@ApiStatus.Experimental
public final class ServerPlayerBedSleepFailEvent extends Cancellable {
    private final Player player;
    private final Reason reason;
    private final BlockPos blockPos;
    private final World world;

    public ServerPlayerBedSleepFailEvent(Player player, Reason reason, BlockPos blockPos, World world) {
        this.player = player;
        this.reason = reason;
        this.blockPos = blockPos;
        this.world = world;
    }

    public Player getPlayer() {
        return player;
    }

    public Reason getReason() {
        return reason;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public World getWorld() {
        return world;
    }

    public enum Reason {
        INVALID_PLAYER,
        TOO_FAR_AWAY,
        BLOCKED,
        MONSTERS_NEAR,
        /** Currently unused. */ MISC
    }
}
