package pl.olafcio.avoid.net.entity_server.event.block;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.world.block_data.BlockData;
import pl.olafcio.avoid.net.world.World;

@ApiStatus.Experimental
public final class ServerEntityBlockTrampleEvent extends Cancellable {
    public final Entity entity;
    public final World world;
    public final BlockData blockData;
    public final BlockPos blockPos;

    public ServerEntityBlockTrampleEvent(Entity entity, World world, BlockData blockData, BlockPos blockPos) {
        this.entity = entity;
        this.world = world;
        this.blockData = blockData;
        this.blockPos = blockPos;
    }
}
