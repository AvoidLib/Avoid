package pl.olafcio.avoid.mods.loader.mod;

import com.google.common.base.CaseFormat;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.mods.annotation_processor.AutoEffect;
import pl.olafcio.avoid.mods.annotation_processor.AutoID;
import pl.olafcio.avoid.net.effect.Effect;
import pl.olafcio.avoid.net.id.Identification;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;

@ApiStatus.Internal
public interface LXEffect {
    default boolean registerAutoEffect(String id, Class<?> klass, String className, AtomicBoolean usedAutoID)
            throws NoSuchMethodException
    {
        if (klass.isAnnotationPresent(AutoEffect.class)) {
            if (!Effect.class.isAssignableFrom(klass)) {
                Avoid.LOGGER.error("@AutoEffect requires the annotated type to extend Effect (avoid.net.effect)");
                return true;
            }

            if (!klass.isAnnotationPresent(AutoID.class)) {
                Avoid.LOGGER.error("@AutoEffect requires the annotated type to be also annotated with @AutoID");
                return true;
            }

            usedAutoID.set(true);

            var simpleName = klass.getSimpleName();

            suffixRemover:
            {
                if (!simpleName.endsWith("Effect")) {
                    Avoid.LOGGER.warn("All effect classes should end with 'Effect', found non-matching: {} ({})", simpleName, className);
                    break suffixRemover;
                }

                simpleName = simpleName.substring(0, simpleName.length() - 6);
            }

            var constructor = klass.getDeclaredConstructor();
            var idstr = id + ":" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);

            Avoid.LOGGER.debug("Registering effect '{}'", idstr);

            try {
                pl.olafcio.avoid.net.effect.Effects.register(Identification.of(idstr), (Effect) constructor.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Failed to construct effect (%s)".formatted(idstr), e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        return false;
    }
}
