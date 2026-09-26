package pl.olafcio.avoid_impl.mods.loader.mod;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.mods.annotation_processor.AutoID;
import pl.olafcio.avoid.mods.annotation_processor.AutoItem;
import pl.olafcio.avoid.net.item.Item;

import java.util.concurrent.atomic.AtomicBoolean;

@ApiStatus.Internal
public interface LXItem {
    default boolean registerAutoItem(String id, Class<?> klass, String className, AtomicBoolean usedAutoID)
            throws NoSuchMethodException
    {
        if (klass.isAnnotationPresent(AutoItem.class)) {
            if (!Item.class.isAssignableFrom(klass)) {
                Avoid.LOGGER.error("@AutoItem requires the annotated type to extend Item (avoid.net.item.custom)");
                return true;
            }

            if (!klass.isAnnotationPresent(AutoID.class)) {
                Avoid.LOGGER.error("@AutoItem requires the annotated type to be also annotated with @AutoID");
                return true;
            }

            usedAutoID.set(true);

            var simpleName = klass.getSimpleName();
            if (!simpleName.endsWith("Item")) {
                Avoid.LOGGER.warn("All item classes should end with 'Item', found non-matching: {} ({})", simpleName, className);
            }
        }

        return false;
    }
}
