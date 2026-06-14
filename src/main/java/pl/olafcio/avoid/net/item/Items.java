package pl.olafcio.avoid.net.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.AvoidPackageOnly;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.values.Rarity;
import pl.olafcio.avoid.net.item.properties.*;
import pl.olafcio.avoid.net.item.values.SlotDescriptionNative;
import pl.olafcio.avoid.net.item.custom.Item;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

@ApiStatus.Experimental
public final class Items {
    @ApiStatus.Internal
    private Items() {}

    private static final HashMap<Item, net.minecraft.world.item.Item> CUSTOM_MAP
                   = new HashMap<>();

    public static BaseComponent<?> getName(Item customItem) {
        var item = CUSTOM_MAP.get(customItem);
        return COFromNative.from(item.getName());
    }

    public static Identification getID(Item customItem) {
        var item = CUSTOM_MAP.get(customItem);
        return IdentificationNative.convertFrom(BuiltInRegistries.ITEM.getKey(item));
    }

    public static void register(Identification blockID, Supplier<? extends Item> constructor) {
        var id = IdentificationNative.convert(blockID);

        Function<Properties, net.minecraft.world.item.Item> callback = properties -> ItemNative.make(
                constructor.get(),
                properties
        );

        net.minecraft.world.item.Items.registerItem(
                ResourceKey.create(Registries.ITEM, id),
                callback,
                getProperties(constructor.get().getClass())
        );
    }

    @ApiStatus.Internal
    public static void register(Identification blockID, Supplier<? extends Item> constructor, AvoidPackageOnly<net.minecraft.world.item.Item> interceptor) {
        var id = IdentificationNative.convert(blockID);

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
            properties = properties.stacksTo(item.getAnnotation(_damage.class)
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

        return properties;
    }
}
