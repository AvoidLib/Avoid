package pl.olafcio.avoid.net.item.custom;

import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.item.values.UseStatus;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player_server.values.HandType;
import pl.olafcio.avoid.net.world.World;

/**
 * @deprecated Use & extend {@link pl.olafcio.avoid.net.item.Item Item} instead.
 */
@NeverRemoval
@Deprecated(since = "v1.26")
public abstract class Item extends pl.olafcio.avoid.net.item.Item {
    //=============//
    // OVERRIDABLE //
    //=============//

    @NeverRemoval
    public ItemStack newStack(ItemStack stack) {
        return stack;
    }

    /**
     * @return {@code null} if the vanilla code should be executed.<br/>
     *         A non-null {@link UseStatus} otherwise.
     */
    @Override
    public UseStatus use(World world, Player player, HandType handType, ItemStack itemStack, @Nullable BlockPos blockPos, @Nullable Direction direction) {
        return null;
    }

    //==========//
    // METADATA //
    //==========//

    @Override
    public final BaseComponent<?> getName() {
        return super.getName();
    }

    @Override
    public final Identification getID() {
        return super.getID();
    }

    @Override
    public final String toString() {
        return "Item{%s}".formatted(getID());
    }
}
