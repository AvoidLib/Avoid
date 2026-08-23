package pl.olafcio.avoid.net.entity.type;

import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.world.vect3.IVect3;

import java.util.UUID;

/**
 * A Minecraft entity object used for all entities that can trade.<br/>
 * These entities are generally called <i>merchants</i>.
 * <br/><br/>
 * There's currently 2 types of merchants:
 * <ul>
 *     <li>villagers,</li>
 *     <li>wandering traders.</li>
 * </ul><br/>
 * While Minecraft kind-of misqualifies wandering traders as "abstract villagers",<br/>
 * that's just the classname.
 * <br/><br/>
 * This class provides additional specific methods available only for
 * merchant entities, such as {@code openTradingScreen}.
 */
public abstract class Merchant extends Entity {
    public Merchant(int id, EntityType type, IVect3 position, IVect3 velocity, UUID uuid, String uuidString, BaseComponent<?> name, net.minecraft.world.entity.Entity underlyingEntity) {
        super(id, type, position, velocity, uuid, uuidString, name, underlyingEntity);
    }

    /**
     * Returns the merchant's unhappy counter.
     */
    public int getUnhappyCounter() {
        return __cast(AbstractVillager.class).getUnhappyCounter();
    }

    /**
     * Sets the merchant's unhappy counter.
     */
    public void setUnhappyCounter(int value) {
        __cast(AbstractVillager.class).setUnhappyCounter(value);
    }

    /**
     * Returns the merchant's XP.
     */
    public int getMerchantXP() {
        return __cast(net.minecraft.world.item.trading.Merchant.class).getVillagerXp();
    }

    /**
     * Sets the merchant's XP.
     * <br/><br/>
     * <b>NOTE:</b> This only works on villagers.
     */
    public void setMerchantXP(int value) {
        __cast(Villager.class).setVillagerXp(value);
    }

    /**
     * Returns whether a player is currently in the merchant's menu.
     */
    public boolean isTrading() {
        return __cast(AbstractVillager.class).isTrading();
    }

    /**
     * Returns the player currently in the merchant's menu.<br/>
     * May be {@code null}.
     */
    public @Nullable Player getTradingPlayer() {
        var player = __cast(net.minecraft.world.item.trading.Merchant.class).getTradingPlayer();
        return player == null ? null : PlayerNative.convertFrom(player);
    }

    /**
     * Sets the player currently in the merchant's menu.<br/>
     * May be {@code null}.
     */
    public void setTradingPlayer(@Nullable Player player) {
        __cast(net.minecraft.world.item.trading.Merchant.class).setTradingPlayer(
                player == null
                        ? null
                        : (net.minecraft.world.entity.player.Player) EntityNative.convert(player)
        );
    }

    /**
     * Returns whether the merchant is still trading with the specified player.
     * <br/><br/>
     * This method should be used instead of {@link #getTradingPlayer()} as it<br/>
     * also checks whether the player is still alive and within a tradeable distance.
    */
    public boolean isTradingWith(@NonNull Player player) {
        return __cast(net.minecraft.world.item.trading.Merchant.class).stillValid(
                (net.minecraft.world.entity.player.Player) EntityNative.convert(player)
        );
    }

    /**
     * Opens the merchant's trading screen for the given player.
     * @param player The player to open the trading screen for.
     * @param title The title to use for the screen.
     * @param merchantLevel The merchant level to show. It is used for the level progress bar.
     */
    @ServerOnly
    public void openTradingScreen(Player player, BaseComponent<?> title, int merchantLevel) {
        if (isClient())
            throw new ImproperEnvironment("[Merchant#openTradingScreen] This method can only be ran on server merchants!");

        __cast(net.minecraft.world.item.trading.Merchant.class).openTradingScreen(
                (net.minecraft.world.entity.player.Player) EntityNative.convert(player),
                COToNative.from(title),
                merchantLevel
        );
    }

    /**
     * Returns whether the merchant can restock.
     */
    public boolean canRestock() {
        return __cast(net.minecraft.world.item.trading.Merchant.class).canRestock();
    }

    /**
     * Returns whether the entity has showing progress bar enabled in its trading screen.
     */
    public boolean showProgressBar() {
        return __cast(net.minecraft.world.item.trading.Merchant.class).showProgressBar();
    }
}
