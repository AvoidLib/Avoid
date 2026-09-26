package pl.olafcio.avoid.net.item;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.item.values.UseStatus;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player_server.values.HandType;
import pl.olafcio.avoid.net.world.World;

@NeverRemoval
public final class NativeItem extends Item {
    NativeItem(Key item) {
        super(item);
    }

    @Override
    public ItemStack newStack(ItemStack stack) {
        return new ItemStack(this, 1);
    }

    @Override
    public UseStatus use(World world, Player player, HandType handType, ItemStack itemStack, @Nullable BlockPos blockPos, @Nullable Direction direction) {
        throw new UnsupportedOperationException("[NativeItem#use] Cannot be used on Velocity");
    }
}
