package pl.olafcio.avoid.net.entity_server.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.mods.event_group.EntityEvent;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.world.World;

import java.util.Objects;

@ApiStatus.Experimental
public final class ServerEntityInsideBlockEvent extends Cancellable implements EntityEvent {
    private final World world;
    private final BlockPos blockPos;
    private final Entity entity;

    public ServerEntityInsideBlockEvent(World world, BlockPos blockPos, Entity entity) {
        this.world = world;
        this.blockPos = blockPos;
        this.entity = entity;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    public World world() {
        return world;
    }
    public BlockPos blockPos() {
        return blockPos;
    }
    public Entity entity() {
        return entity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ServerEntityInsideBlockEvent) obj;
        return Objects.equals(this.world, that.world) &&
                Objects.equals(this.blockPos, that.blockPos) &&
                Objects.equals(this.entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, blockPos, entity);
    }

    @Override
    public String toString() {
        return "ServerEntityInsideBlockEvent[" +
                "world=" + world + ", " +
                "blockPos=" + blockPos + ", " +
                "entity=" + entity + ']';
    }
}
