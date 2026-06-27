package pl.olafcio.avoid.net.player_server.event.block.anvil;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Forceable;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.item.stack.ItemStack;

@ApiStatus.Experimental
public final class ServerPlayerAnvilEnchantmentCompatibilityCheckEvent extends Forceable {
    private final ItemStack item1;
    private final ItemStack item2;

    private final Identification enchant1;
    private final Identification enchant2;

    public ServerPlayerAnvilEnchantmentCompatibilityCheckEvent(
            boolean compatible,

            ItemStack item1,
            ItemStack item2,

            Identification enchant1,
            Identification enchant2
    ) {
        super(compatible);
        this.item1 = item1;
        this.item2 = item2;
        this.enchant1 = enchant1;
        this.enchant2 = enchant2;
    }

    public ItemStack getItem1() {
        return item1;
    }

    public ItemStack getItem2() {
        return item2;
    }

    public Identification getEnchant1() {
        return enchant1;
    }

    public Identification getEnchant2() {
        return enchant2;
    }
}
