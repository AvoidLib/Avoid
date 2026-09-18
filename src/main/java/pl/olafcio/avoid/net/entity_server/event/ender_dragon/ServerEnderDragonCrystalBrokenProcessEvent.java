package pl.olafcio.avoid.net.entity_server.event.ender_dragon;

import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.values.Damage;
import pl.olafcio.avoid.net.world.World;

/**
 * <b>Note:</b> You cannot cancel breaking the end crystal with this event,<br/>
 * &emsp;&emsp;&emsp;however you can cancel the affectance of it on the ender dragon.
 */
public final class ServerEnderDragonCrystalBrokenProcessEvent extends Cancellable {
    private final World world;
    private final Entity crystal;
    private final BlockPos blockPos;
    private final Damage damage;

    public ServerEnderDragonCrystalBrokenProcessEvent(World world, Entity crystal, BlockPos blockPos, Damage damage) {
        this.world = world;
        this.crystal = crystal;
        this.blockPos = blockPos;
        this.damage = damage;
    }

    public World getWorld() {
        return world;
    }

    public Entity getCrystal() {
        return crystal;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public Damage getDamage() {
        return damage;
    }
}
