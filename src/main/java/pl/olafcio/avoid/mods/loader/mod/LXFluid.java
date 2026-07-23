package pl.olafcio.avoid.mods.loader.mod;

import com.google.common.base.CaseFormat;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.mods.annotation_processor.AutoEntity;
import pl.olafcio.avoid.mods.annotation_processor.AutoFluid;
import pl.olafcio.avoid.mods.annotation_processor.AutoID;
import pl.olafcio.avoid.net.entity.custom.Entity;
import pl.olafcio.avoid.net.entity_type.EntityTypes;
import pl.olafcio.avoid.net.fluid.Fluid;
import pl.olafcio.avoid.net.fluid.Fluids;
import pl.olafcio.avoid.net.id.Identification;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;

@ApiStatus.Internal
public interface LXFluid {
    default boolean registerAutoFluid(String id, Class<?> klass, String className, AtomicBoolean usedAutoID)
            throws NoSuchMethodException
    {
        if (klass.isAnnotationPresent(AutoFluid.class)) {
            if (!Fluid.class.isAssignableFrom(klass)) {
                Avoid.LOGGER.error("@AutoFluid requires the annotated type to extend Fluid (avoid.net.fluid)");
                return true;
            }

            if (!klass.isAnnotationPresent(AutoID.class)) {
                Avoid.LOGGER.error("@AutoFluid requires the annotated type to be also annotated with @AutoID");
                return true;
            }

            usedAutoID.set(true);

            var simpleName = klass.getSimpleName();

            suffixRemover:
            {
                if (!simpleName.endsWith("Fluid")) {
                    Avoid.LOGGER.warn("All fluid classes should end with 'Fluid', found non-matching: {} ({})", simpleName, className);
                    break suffixRemover;
                }

                simpleName = simpleName.substring(0, simpleName.length() - 5);
            }

            var constructor = klass.getDeclaredConstructor();
            var idstr = id + ":" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);

            Avoid.LOGGER.debug("Registering entity '{}'", idstr);

            try {
                Fluids.register(Identification.of(idstr), (Fluid) constructor.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Failed to construct fluid (%s)".formatted(idstr), e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        return false;
    }
}
