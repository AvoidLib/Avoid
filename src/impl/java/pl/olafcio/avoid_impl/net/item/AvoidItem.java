package pl.olafcio.avoid_impl.net.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.item.Item;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;
import pl.olafcio.avoid.net.item.values.UseStatusNative;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.player_server.values.HandTypeNative;
import pl.olafcio.avoid.net.world.WorldNative;

public class AvoidItem extends net.minecraft.world.item.Item {
    public Item item;

    public AvoidItem(Properties properties, Item item) {
        super(properties);
        this.item = item;
    }

    public AvoidItem(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ItemStack getDefaultInstance() {
        var stack = super.getDefaultInstance();
        var converted = ItemStackNative.convertFrom(stack);

        converted = item.newStack(converted);

        return ItemStackNative.convert(converted);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        var ret = item.use(
                WorldNative.make(level),
                PlayerNative.convertFrom(player),
                HandTypeNative.convert(interactionHand),
                ItemStackNative.convertFrom(player.getItemInHand(interactionHand)),
                null,
                null
        );

        if (ret == null)
            return super.use(level, player, interactionHand);

        return UseStatusNative.convert(ret);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        var ret = item.use(
                WorldNative.make(useOnContext.getLevel()),
                PlayerNative.convertFrom(useOnContext.getPlayer()),
                HandTypeNative.convert(useOnContext.getHand()),
                ItemStackNative.convertFrom(useOnContext.getItemInHand()),
                BlockPosNative.convert(useOnContext.getClickedPos()),
                Direction.valueOf(useOnContext.getHorizontalDirection().name())
        );

        if (ret == null)
            return super.useOn(useOnContext);

        return UseStatusNative.convert(ret);
    }
}
