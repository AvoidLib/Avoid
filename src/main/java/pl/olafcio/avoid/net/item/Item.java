package pl.olafcio.avoid.net.item;

import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.map.ItemComponentMap;
import pl.olafcio.avoid.net.item.custom.AbstractItem;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.item.values.UseStatus;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player_server.values.HandType;
import pl.olafcio.avoid.net.world.World;

@NeverRemoval
public abstract class Item extends AbstractItem {
    final net.minecraft.world.item.Item item;

    /**
     * @apiNote Never construct your Item classes! <i>(it will throw an error)</i><br/>
     *          You probably want to use an {@link ItemStack} instead.
     */
    @ApiStatus.Internal
    protected Item() {
        if (ItemNative.itemconstruct.containsKey(Thread.currentThread().threadId()))
            this.item = ItemNative.itemconstruct.remove(Thread.currentThread().threadId());
        else
            throw new UnsupportedOperationException("Item classes cannot be constructed!");
    }

    Item(net.minecraft.world.item.Item item) {
        this.item = item;
    }

    //==========//
    // METADATA //
    //==========//

    @NeverRemoval
    @ApiStatus.NonExtendable
    public BaseComponent<?> getName() {
        return COFromNative.from(item.getName());
    }

    @NeverRemoval
    @ApiStatus.NonExtendable
    public Identification getID() {
        return IdentificationNative.convertFrom(BuiltInRegistries.ITEM.getKey(item));
    }

    @NeverRemoval
    @ApiStatus.NonExtendable
    public ItemComponentMap getComponents() {
        return new ItemComponentMap(item.components());
    }

    //==============//
    // OVERRIDEABLE //
    //==============//

    @NeverRemoval
    public ItemStack newStack(ItemStack stack) {
        return stack;
    }

    /**
     * @return {@code null} if the vanilla code should be executed.<br/>
     *         A non-null {@link UseStatus} otherwise.
     */
    @NeverRemoval
    public UseStatus use(World world, Player player, HandType handType, ItemStack itemStack, @Nullable BlockPos blockPos, @Nullable Direction direction) {
        return null;
    }

    //==========//
    // TOSTRING //
    //==========//

    @Override
    public String toString() {
        return "Item{%s}".formatted(getID());
    }

    //====//
    // OF //
    //====//

    @NeverRemoval
    public static Item of(Identification id) {
        var mcItem = BuiltInRegistries.ITEM.getValue(IdentificationNative.convert(id));
        if (mcItem instanceof AvoidItem avoid)
            return avoid.item;

        return new NativeItem(mcItem);
    }

    @NeverRemoval
    public static Item of(String id) {
        var mcItem = BuiltInRegistries.ITEM.getValue(IdentificationNative.convert(id));
        if (mcItem instanceof AvoidItem avoid)
            return avoid.item;

        return new NativeItem(mcItem);
    }

    //====//
    // IS //
    //====//

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
