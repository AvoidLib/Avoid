package pl.olafcio.avoid.mods.loader.mod;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.mods.annotation_processor.AutoChar;
import pl.olafcio.avoid.mods.annotation_processor.AutoSelector;
import pl.olafcio.avoid.net.entity_selector.EntitySelector;
import pl.olafcio.avoid.net.entity_selector.EntitySelectors;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;

@ApiStatus.Internal
public interface LXSelector {
    default boolean registerAutoSelector(String id, Class<?> klass, String className, AtomicBoolean usedAutoChar)
            throws NoSuchMethodException
    {
        if (klass.isAnnotationPresent(AutoSelector.class)) {
            if (!EntitySelector.class.isAssignableFrom(klass)) {
                Avoid.LOGGER.error("@AutoSelector requires the annotated type to extend EntitySelector (avoid.net.entity_selector)");
                return true;
            }

            if (!klass.isAnnotationPresent(AutoChar.class)) {
                Avoid.LOGGER.error("@AutoSelector requires the annotated type to be also annotated with @AutoChar");
                return true;
            }

            usedAutoChar.set(true);

            var simpleName = klass.getSimpleName();
            if (!simpleName.endsWith("Selector")) {
                Avoid.LOGGER.warn("All selector classes should end with 'Selector', found non-matching: {} ({})", simpleName, className);
            }

            var constructor = klass.getDeclaredConstructor();
            var ch = klass.getAnnotation(AutoChar.class)
                    .value();

            Avoid.LOGGER.debug("Registering selector '@{}' ({})", ch, id);

            EntitySelectors.register(ch, () -> {
                try {
                    return (EntitySelector) constructor.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Failed to construct entity selector (@%s) [%s]".formatted(ch, id), e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return false;
    }
}
