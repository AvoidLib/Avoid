package pl.olafcio.avoid.net.item;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid_impl.mods.loader.AvoidPackageOnly;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.item.properties.*;

import java.util.function.Function;
import java.util.function.Supplier;

@NeverRemoval
public final class Items {
    @ApiStatus.Internal
    private Items() {}

    /**
     * @deprecated Use {@link Item#getName()} instead.
     */
    @Deprecated(since = "v1.26")
    public static BaseComponent<?> getName(Item customItem) {
        return customItem.getName();
    }

    /**
     * @deprecated Use {@link Item#getID()} instead.
     */
    @Deprecated(since = "v1.26")
    public static Identification getID(Item customItem) {
        return customItem.getID();
    }

    /**
     * @deprecated Use {@link Items#register(Identification, Function, Class)} instead.
     */
    @Deprecated(since = "v1.26", forRemoval = true)
    public static void register(Identification itemID, Supplier<? extends Item> constructor) {
        pl.olafcio.avoid_impl.net.item.Items.register(itemID, constructor);
    }

    public static void register(Identification itemID, Function<net.minecraft.world.item.Item, ? extends Item> constructor, Class<? extends Item> itemClass) {
        pl.olafcio.avoid_impl.net.item.Items.register(itemID, constructor, itemClass);
    }

    @ApiStatus.Internal
    public static void register(Identification itemID, Supplier<? extends Item> constructor, AvoidPackageOnly<net.minecraft.world.item.Item> interceptor) {
        pl.olafcio.avoid_impl.net.item.Items.register(itemID, constructor, interceptor);
    }
}
