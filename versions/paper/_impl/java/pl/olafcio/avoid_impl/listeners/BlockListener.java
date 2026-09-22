package pl.olafcio.avoid.listeners;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import pl.olafcio.avoid.Util;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_server.event.ServerEntityInteractEvent;
import pl.olafcio.avoid.net.entity_server.event.block.ServerEntityBlockTrampleEvent;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.world.WorldNative;
import pl.olafcio.avoid.net.world.block_data.BlockDataNative;

public final class BlockListener implements Listener {
    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent bukkitEvent) {
        if (bukkitEvent.getBlock().getType() == Material.FARMLAND) {
            var entity = Util.convertEntity(bukkitEvent.getEntity());
            var location = bukkitEvent.getBlock().getLocation();
            var blockPos = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());

            var event = new ServerEntityBlockTrampleEvent(
                    EntityNative.convertFrom(entity),
                    WorldNative.make(entity.level()),
                    BlockDataNative.convertFrom(entity.level().getBlockState(blockPos)),
                    BlockPosNative.convert(blockPos)
            );

            EventManager.fire(event);

            if (event.isCancelled())
                bukkitEvent.setCancelled(true);
        }
    }
}
