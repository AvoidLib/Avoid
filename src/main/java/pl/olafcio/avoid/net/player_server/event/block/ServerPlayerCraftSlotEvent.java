package pl.olafcio.avoid.net.player_server.event.block;

import pl.olafcio.avoid.net.item.Item;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.world.World;

/**
 * An event triggered when the user changes or interacts with any crafting slots.<br/><br/>
 * You cannot cancel this event, however you may:
 * <ul>
 * <li>use the {@link ServerPlayerCraftSlotEvent#setItemStack} method,</li>
 * <li>to set it to {@link Item#of(String)} with the argument set as {@code "AIR"}.</li>
 * </ul><br/>
 * (essentially equivalent to cancelling the event)
 * <br/><br/>
 * <b>Note:</b> This event may be called <u>A LOT</u> of times.
 */
// TODO: Accessing items used to get the resulting itemStack.
public final class ServerPlayerCraftSlotEvent {
    private final Player player;
    private final World world;
    private final ItemStack originalItemStack;

    private ItemStack itemStack;
    private boolean modified;

    public ServerPlayerCraftSlotEvent(Player player, World world, ItemStack itemStack) {
        this.player = player;
        this.world = world;
        this.itemStack =
        this.originalItemStack = itemStack;
        this.modified = false;
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemStack getOriginalItemStack() {
        return originalItemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.modified = true;
    }

    public boolean isModified() {
        return modified;
    }
}
