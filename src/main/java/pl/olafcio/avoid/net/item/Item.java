package pl.olafcio.avoid.net.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.map.ItemComponentMap;
import pl.olafcio.avoid.net.item.custom.AbstractItem;
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
public final class Item extends AbstractItem {
    final net.minecraft.world.item.Item item;

    Item(net.minecraft.world.item.Item item) {
        this.item = item;
    }

    @NeverRemoval
    public BaseComponent<?> getName() {
        return COFromNative.from(item.getName());
    }

    @ApiStatus.Experimental
    public Identification getID() {
        return IdentificationNative.convertFrom(BuiltInRegistries.ITEM.getKey(item));
    }

    @NeverRemoval
    public ItemComponentMap getComponents() {
        return new ItemComponentMap(item.components());
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

    @NeverRemoval
    public String toString() {
        return BuiltInRegistries.ITEM.wrapAsHolder(item).getRegisteredName();
    }

    @NeverRemoval
    public static Item of(Identification id) {
        var mcItem = BuiltInRegistries.ITEM.getValue(IdentificationNative.convert(id));
        return new Item(mcItem);
    }

    @NeverRemoval
    public static Item of(String id) {
        var mcItem = BuiltInRegistries.ITEM.getValue(IdentificationNative.convert(id));
        return new Item(mcItem);
    }

    @ApiStatus.Experimental
    public boolean is(String id) {
        return Identification.of(id).equals(getID());
    }

    @ApiStatus.Experimental
    public boolean is(Identification id) {
        return getID().equals(id);
    }

    @ApiStatus.Experimental
    public boolean is(String namespace, String path) {
        return getID().is(namespace, path);
    }
}
