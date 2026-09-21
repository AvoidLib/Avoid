package pl.olafcio.avoid_impl.net.item;

public class ItemNative {
    public static net.minecraft.world.item.Item make(pl.olafcio.avoid.net.item.Item item, net.minecraft.world.item.Item.Properties properties) {
        return new AvoidItem(properties, item);
    }
}
