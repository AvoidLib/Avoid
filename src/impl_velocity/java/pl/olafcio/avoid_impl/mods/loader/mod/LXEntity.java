package pl.olafcio.avoid_impl.mods.loader.mod;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.mods.annotation_processor.AutoEntity;
import pl.olafcio.avoid.mods.annotation_processor.AutoID;
import pl.olafcio.avoid.net.entity.custom.Entity;
import pl.olafcio.avoid.net.entity.custom.Merchant;

import java.util.concurrent.atomic.AtomicBoolean;

@ApiStatus.Internal
public interface LXEntity {
    default boolean registerAutoEntity(String id, Class<?> klass, String className, AtomicBoolean usedAutoID)
            throws NoSuchMethodException
    {
        if (klass.isAnnotationPresent(AutoEntity.class)) {
            if (!Entity.class.isAssignableFrom(klass) && !Merchant.class.isAssignableFrom(klass)) {
                Avoid.LOGGER.error("@AutoEntity requires the annotated type to extend Entity or Merchant (avoid.net.entity.custom)");
                return true;
            }

            if (!klass.isAnnotationPresent(AutoID.class)) {
                Avoid.LOGGER.error("@AutoEntity requires the annotated type to be also annotated with @AutoID");
                return true;
            }

            usedAutoID.set(true);

            var simpleName = klass.getSimpleName();
            if (!simpleName.endsWith("Entity")) {
                Avoid.LOGGER.warn("All entity classes should end with 'Entity', found non-matching: {} ({})", simpleName, className);
            }
        }

        return false;
    }
}
