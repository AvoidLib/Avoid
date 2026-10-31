package pl.olafcio.avoid.net.item;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid_impl.mods.loader.AvoidPackageOnly;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.id.Identification;

import java.lang.reflect.AnnotatedElement;
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
     * @deprecated Use {@link Items#register(Identification, Supplier, AnnotatedElement)} instead.
     */
    @Deprecated(since = "v1.26", forRemoval = true)
    public static void register(Identification itemID, Supplier<? extends Item> constructor) {
        pl.olafcio.avoid_impl.net.item.Items.register(itemID, constructor);
    }

    /**
     * @deprecated Use {@link Items#register(Identification, Supplier, AnnotatedElement)} instead.
     */
    @Deprecated(since = "v2.1", forRemoval = true)
    public static void register(Identification itemID, Function<net.minecraft.world.item.Item, ? extends Item> constructor, Class<? extends Item> itemClass) {
        pl.olafcio.avoid_impl.net.item.Items.register(itemID, constructor, itemClass);
    }

    @ApiStatus.Experimental
    public static void register(Identification itemID, Supplier<? extends Item> constructor, AnnotatedElement config) {
        pl.olafcio.avoid_impl.net.item.Items.register(itemID, constructor, config);
    }

    @ApiStatus.Internal
    public static void register(Identification itemID, Supplier<? extends Item> constructor, AvoidPackageOnly<net.minecraft.world.item.Item> interceptor) {
        pl.olafcio.avoid_impl.net.item.Items.register(itemID, constructor, interceptor);
    }
}
