package pl.olafcio.avoid.net.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;
import pl.olafcio.avoid.net.item.values.UseStatus;
import pl.olafcio.avoid.net.item.values.UseStatusNative;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player_server.values.HandType;
import pl.olafcio.avoid.net.player_server.values.HandTypeNative;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.WorldNative;

@NeverRemoval
public final class NativeItem extends Item {
    NativeItem(net.minecraft.world.item.Item item) {
        super(item);
    }

    @Override
    public ItemStack newStack(ItemStack stack) {
        return ItemStackNative.convertFrom(item.getDefaultInstance());
    }

    @Override
    public UseStatus use(World world, Player player, HandType handType, ItemStack itemStack, @Nullable BlockPos blockPos, @Nullable Direction direction) {
        InteractionResult ret;

        if (blockPos == null) {
            ret = item.use(
                    WorldNative.convert(world),
                    (net.minecraft.world.entity.player.Player) EntityNative.convert(player),
                    HandTypeNative.convertFrom(handType)
            );
        } else {
            var plr = (net.minecraft.world.entity.player.Player) EntityNative.convert(player);

            ret = item.useOn(
                    new UseOnContext(
                        WorldNative.convert(world),
                        plr,
                        HandTypeNative.convertFrom(handType),
                        ItemStackNative.convert(itemStack),
                        new BlockHitResult(plr.position(), net.minecraft.core.Direction.valueOf(direction.name()), BlockPosNative.convertFrom(blockPos), true)
                    )
            );
        }

        return UseStatusNative.convert(ret);
    }
}
