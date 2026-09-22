package pl.olafcio.avoid.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import pl.olafcio.avoid.Util;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.player_server.event.food.ServerPlayerSetFoodLevelEvent;

public final class FoodListener implements Listener {
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent bukkitEvent) {
        if (bukkitEvent.getEntity() instanceof Player player) {
            var event = new ServerPlayerSetFoodLevelEvent(
                    PlayerNative.convertFrom(Util.convert(player)),
                    bukkitEvent.getFoodLevel(),
                    bukkitEvent.getEntity().getFoodLevel()
            );

            EventManager.fire(event);

            if (event.isCancelled())
                bukkitEvent.setCancelled(true);
        }
    }
}
