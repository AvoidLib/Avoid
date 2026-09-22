package pl.olafcio.avoid.listeners;

import net.minecraft.world.item.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MenuType;
import pl.olafcio.avoid.Util;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_server.event.ServerEntityInteractEvent;
import pl.olafcio.avoid.net.item.Item;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.player_server.event.block.ServerPlayerCraftSlotEvent;
import pl.olafcio.avoid.net.player_server.event.block.ServerPlayerSmithingSlotEvent;
import pl.olafcio.avoid.net.player_server.event.block.anvil.ServerPlayerAnvilSuccessResultEvent;
import pl.olafcio.avoid.net.world.WorldNative;

public final class InventoryListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent bukkitEvent) {
        var inv = bukkitEvent.getClickedInventory();
        if (inv != null) {
            if (inv.getType().getMenuType() == MenuType.ANVIL) {
                if (bukkitEvent.getSlot() != 2) {
                    var result = bukkitEvent.getClickedInventory().getItem(2);
                    if (result.isEmpty())
                        return;

                    var itemA = bukkitEvent.getClickedInventory().getItem(0);
                    var itemB = bukkitEvent.getClickedInventory().getItem(1);

                    var event = new ServerPlayerAnvilSuccessResultEvent(
                            itemA == null ? null : ItemStackNative.convertFrom(Util.convert(itemA)),
                            itemB == null ? null : ItemStackNative.convertFrom(Util.convert(itemB)),
                            ItemStackNative.convertFrom(Util.convert(result))
                    );

                    EventManager.fire(event);

                    if (event.isModified())
                        bukkitEvent.getClickedInventory().setItem(2, Util.convertFrom(ItemStackNative.convert(event.getResult())));
                }
            } else if (inv.getType().getMenuType() == MenuType.SMITHING) {
                var event = new ServerPlayerSmithingSlotEvent(
                        PlayerNative.convertFrom(Util.convert((Player) bukkitEvent.getWhoClicked())),
                        WorldNative.make(Util.convert(bukkitEvent.getWhoClicked().getWorld())),
                        ItemStackNative.convertFrom(
                                bukkitEvent.getCurrentItem() == null
                                        ? Items.AIR.getDefaultInstance()
                                        : Util.convert(bukkitEvent.getCurrentItem())
                        )
                );

                EventManager.fire(event);

                if (event.isModified())
                    bukkitEvent.getClickedInventory().setItem(bukkitEvent.getSlot(), Util.convertFrom(ItemStackNative.convert(event.getItemStack())));
            } else if (inv == bukkitEvent.getInventory()) {
                var event = new ServerPlayerCraftSlotEvent(
                        PlayerNative.convertFrom(Util.convert((Player) bukkitEvent.getWhoClicked())),
                        WorldNative.make(Util.convert(bukkitEvent.getWhoClicked().getWorld())),
                        ItemStackNative.convertFrom(
                                bukkitEvent.getCurrentItem() == null
                                        ? Items.AIR.getDefaultInstance()
                                        : Util.convert(bukkitEvent.getCurrentItem())
                        )
                );

                EventManager.fire(event);

                if (event.isModified())
                    bukkitEvent.getClickedInventory().setItem(bukkitEvent.getSlot(), Util.convertFrom(ItemStackNative.convert(event.getItemStack())));
            }
        }
    }
}
