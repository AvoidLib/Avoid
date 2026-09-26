package pl.olafcio.avoid.net.item;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid_impl.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.map.ItemComponentMap;
import pl.olafcio.avoid.net.item.custom.AbstractItem;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.item.values.UseStatus;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player_server.values.HandType;
import pl.olafcio.avoid.net.world.World;

@NeverRemoval
public abstract class Item extends AbstractItem {
    /**
     * @apiNote Never construct your Item classes! <i>(it will throw an error)</i><br/>
     *          You probably want to use an {@link ItemStack} instead.
     */
    @ApiStatus.Internal
    protected Item() {
        throw new UnsupportedOperationException("Item classes cannot be constructed!");
    }

    private final Key key;

    Item(Key key) {
        this.key = key;
    }

    //==========//
    // METADATA //
    //==========//

    @NeverRemoval
    @ApiStatus.NonExtendable
    public BaseComponent<?> getName() {
        throw new UnsupportedOperationException("[Item#getName] Cannot be used on Velocity");
    }

    @NeverRemoval
    @ApiStatus.NonExtendable
    public Identification getID() {
        return IdentificationNative.convertFrom(key);
    }

    @NeverRemoval
    @ApiStatus.NonExtendable
    public ItemComponentMap getComponents() {
        throw new UnsupportedOperationException("[Item#getComponents] Cannot be used on Velocity");
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
        return new NativeItem(IdentificationNative.convert(id));
    }

    @NeverRemoval
    public static Item of(String id) {
        return new NativeItem(IdentificationNative.convert(id));
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
