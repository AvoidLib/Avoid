package pl.olafcio.avoid.mods.loader.mod;

import com.google.common.base.CaseFormat;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.mods.annotation_processor.AutoEntity;
import pl.olafcio.avoid.mods.annotation_processor.AutoID;
import pl.olafcio.avoid.net.entity.custom.Entity;
import pl.olafcio.avoid.net.entity_type.EntityTypes;
import pl.olafcio.avoid.net.id.Identification;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;

@ApiStatus.Internal
public interface LXEntity {
    default boolean registerAutoEntity(String id, Class<?> klass, String className, AtomicBoolean usedAutoID)
            throws NoSuchMethodException
    {
        if (klass.isAnnotationPresent(AutoEntity.class)) {
            if (!Entity.class.isAssignableFrom(klass)) {
                Avoid.LOGGER.error("@AutoEntity requires the annotated type to extend Entity (avoid.net.entity.custom)");
                return true;
            }

            if (!klass.isAnnotationPresent(AutoID.class)) {
                Avoid.LOGGER.error("@AutoEntity requires the annotated type to be also annotated with @AutoID");
                return true;
            }

            usedAutoID.set(true);

            var simpleName = klass.getSimpleName();

            suffixRemover:
            {
                if (!simpleName.endsWith("Entity")) {
                    Avoid.LOGGER.warn("All entity classes should end with 'Entity', found non-matching: {} ({})", simpleName, className);
                    break suffixRemover;
                }

                simpleName = simpleName.substring(0, simpleName.length() - 6);
            }

            var constructor = klass.getDeclaredConstructor(int.class, Object[].class);
            var idstr = id + ":" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);

            Avoid.LOGGER.debug("Registering entity '{}'", idstr);

            EntityTypes.register(Identification.of(idstr), (Class<? extends Entity>) klass, (arg1, args) -> {
                try {
                    return (Entity) constructor.newInstance(arg1, args);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Failed to construct entity (%s)".formatted(idstr), e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return false;
    }
}
