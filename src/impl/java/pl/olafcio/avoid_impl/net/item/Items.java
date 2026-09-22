package pl.olafcio.avoid_impl.net.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.net.item.Item;
import pl.olafcio.avoid_impl.mods.loader.AvoidPackageOnly;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.values.Rarity;
import pl.olafcio.avoid.net.item.properties.*;
import pl.olafcio.avoid.net.item.properties.spawnegg.ID;
import pl.olafcio.avoid.net.item.values.SlotDescriptionNative;

import java.util.function.Function;
import java.util.function.Supplier;

@ApiStatus.Internal
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
        var id = IdentificationNative.convert(itemID);

        Function<Properties, net.minecraft.world.item.Item> callback = properties -> pl.olafcio.avoid_impl.net.item.ItemNative.make(
                constructor.get(),
                properties
        );

        net.minecraft.world.item.Items.registerItem(
                ResourceKey.create(Registries.ITEM, id),
                callback,
                getProperties(constructor.get().getClass())
        );
    }

    public static void register(Identification itemID, Function<net.minecraft.world.item.Item, ? extends Item> constructor, Class<? extends Item> itemClass) {
        var id = IdentificationNative.convert(itemID);

        Function<Properties, net.minecraft.world.item.Item> callback = properties -> {
            var mcItem = new AvoidItem(properties);

            mcItem.item = constructor.apply(mcItem);

            return mcItem;
        };

        net.minecraft.world.item.Items.registerItem(
                ResourceKey.create(Registries.ITEM, id),
                callback,
                getProperties(itemClass)
        );
    }

    @ApiStatus.Internal
    public static void register(Identification itemID, Supplier<? extends Item> constructor, AvoidPackageOnly<net.minecraft.world.item.Item> interceptor) {
        var id = IdentificationNative.convert(itemID);

        Function<Properties, net.minecraft.world.item.Item> callback = properties -> ItemNative.make(
                constructor.get(),
                properties
        );

        interceptor.value = net.minecraft.world.item.Items.registerItem(
                ResourceKey.create(Registries.ITEM, id),
                callback,
                getProperties(constructor.get().getClass())
        );
    }

    private static Properties getProperties(Class<? extends Item> item) {
        var properties = new Properties();

        if (item.isAnnotationPresent(_useCooldown.class))
            properties = properties.useCooldown(item.getAnnotation(_useCooldown.class)
                                                    .value());

        if (item.isAnnotationPresent(_durability.class))
            properties = properties.durability(item.getAnnotation(_durability.class)
                                                   .value());

        if (item.isAnnotationPresent(_stacksTo.class))
            properties = properties.stacksTo(item.getAnnotation(_stacksTo.class)
                                                 .value());

        if (item.isAnnotationPresent(_damage.class))
            properties = properties.component(DataComponents.MAX_DAMAGE, item.getAnnotation(_damage.class)
                                                                             .value());

        if (item.isAnnotationPresent(_rarity.class))
            properties = properties.rarity(new Rarity.Controller().untransform(item.getAnnotation(_rarity.class)
                                                                                   .value()));

        if (item.isAnnotationPresent(_fireResistant.class))
            properties = properties.fireResistant();

        if (item.isAnnotationPresent(_enchantable.class))
            properties = properties.enchantable(item.getAnnotation(_enchantable.class)
                                                    .value());

        if (item.isAnnotationPresent(_useItemLanguage.class))
            properties = properties.useItemDescriptionPrefix();

        if (item.isAnnotationPresent(_useBlockLanguage.class)) {
            properties = properties.useBlockDescriptionPrefix();

            if (item.isAnnotationPresent(_useItemLanguage.class))
                Avoid.LOGGER.warn("@_useBlockLanguage and @_useItemLanguage present; item-language declaration is overwritten");
        }

        if (item.isAnnotationPresent(_equippable.class))
            properties = properties.equippable(SlotDescriptionNative.get(item.getAnnotation(_equippable.class)
                                                                             .value()));

        if (item.isAnnotationPresent(_equippableUnswappable.class)) {
            properties = properties.equippableUnswappable(SlotDescriptionNative.get(item.getAnnotation(_equippable.class)
                                                                                        .value()));

            if (item.isAnnotationPresent(_equippable.class))
                Avoid.LOGGER.warn("@_equippableUnswappable and @_equippable present; equippable declaration is overwritten");
        }

        if (item.isAnnotationPresent(_spawnEgg.class)) {
            ID id = item.getAnnotation(_spawnEgg.class)
                        .entityType();

            properties = properties.spawnEgg(BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.fromNamespaceAndPath(id.namespace(), id.path())));
        }

        return properties;
    }
}
