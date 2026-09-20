package pl.olafcio.avoid.net.item.custom;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.item.Item;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.item.values.UseStatus;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player_server.values.HandType;
import pl.olafcio.avoid.net.world.World;

@NeverRemoval
public abstract class AbstractItem {
    @ApiStatus.Internal
    protected AbstractItem() {
        if (this.getClass() != Item.class && !(this instanceof pl.olafcio.avoid.net.item.custom.Item))
            throw new UnsupportedOperationException("AbstractItem can be only extended by internal AvoidLib classes; " +
                                                    "if you wanted to create a custom item, extend Item (avoid.net.item.custom)");
    }

    @NeverRemoval public abstract BaseComponent<?> getName();
    @NeverRemoval public abstract Identification getID();
    @NeverRemoval public abstract UseStatus use(World world, Player player, HandType handType, ItemStack itemStack, @Nullable BlockPos blockPos, @Nullable Direction direction);

    public abstract String toString();
}
