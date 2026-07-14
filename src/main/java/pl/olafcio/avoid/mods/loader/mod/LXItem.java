package pl.olafcio.avoid.mods.loader.mod;

import com.google.common.base.CaseFormat;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.mods.annotation_processor.AutoID;
import pl.olafcio.avoid.mods.annotation_processor.AutoItem;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.item.custom.Item;

import java.lang.reflect.InvocationTargetException;
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

            suffixRemover:
            {
                if (!simpleName.endsWith("Item")) {
                    Avoid.LOGGER.warn("All item classes should end with 'Item', found non-matching: {} ({})", simpleName, className);
                    break suffixRemover;
                }

                simpleName = simpleName.substring(0, simpleName.length() - 4);
            }

            var constructor = klass.getDeclaredConstructor();
            var idstr = id + ":" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);

            Avoid.LOGGER.debug("Registering item '{}'", idstr);

            pl.olafcio.avoid.net.item.Items.register(Identification.of(idstr), () -> {
                try {
                    return (Item) constructor.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Failed to construct item (%s)".formatted(idstr), e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return false;
    }
}
