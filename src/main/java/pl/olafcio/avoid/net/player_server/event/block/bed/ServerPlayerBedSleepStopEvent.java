package pl.olafcio.avoid.net.player_server.event.block.bed;

import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.world.World;

public final class ServerPlayerBedSleepStopEvent extends Cancellable {
    private final Player player;
    private final World world;

    private final boolean shouldUpdatePlayerList;
    private final boolean shouldResetCounter;

    public ServerPlayerBedSleepStopEvent(
            Player player, World world,
            boolean shouldUpdatePlayerList, boolean shouldResetCounter
    ) {
        this.player = player;
        this.world = world;
        this.shouldUpdatePlayerList = shouldUpdatePlayerList;
        this.shouldResetCounter = shouldResetCounter;
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public boolean shouldResetCounter() {
        return shouldResetCounter;
    }

    public boolean shouldUpdatePlayerList() {
        return shouldUpdatePlayerList;
    }
}
