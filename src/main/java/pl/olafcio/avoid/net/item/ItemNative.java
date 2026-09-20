package pl.olafcio.avoid.net.item;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class ItemNative {
    @ApiStatus.Internal
    private ItemNative() {}

    public static final Long2ObjectMap<net.minecraft.world.item.Item> itemconstruct
                      = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap<>());

    public static pl.olafcio.avoid.net.item.Item convert(net.minecraft.world.item.Item item) {
        return new NativeItem(item);
    }

    public static net.minecraft.world.item.Item convert(Item item) {
        return item.item;
    }

    public static net.minecraft.world.item.Item make(pl.olafcio.avoid.net.item.Item item, net.minecraft.world.item.Item.Properties properties) {
        return new AvoidItem(properties, item);
    }
}
