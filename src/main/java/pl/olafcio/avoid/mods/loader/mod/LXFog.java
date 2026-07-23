package pl.olafcio.avoid.mods.loader.mod;

import com.google.common.base.CaseFormat;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.mods.annotation_processor.AutoFluid;
import pl.olafcio.avoid.mods.annotation_processor.AutoFog;
import pl.olafcio.avoid.mods.annotation_processor.AutoID;
import pl.olafcio.avoid.net.fluid.Fluid;
import pl.olafcio.avoid.net.fluid.Fluids;
import pl.olafcio.avoid.net.fog.Fog;
import pl.olafcio.avoid.net.fog.Fogs;
import pl.olafcio.avoid.net.id.Identification;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;

@ApiStatus.Internal
public interface LXFog {
    default boolean registerAutoFog(String id, Class<?> klass, String className)
            throws NoSuchMethodException
    {
        if (klass.isAnnotationPresent(AutoFog.class)) {
            if (!Fog.class.isAssignableFrom(klass)) {
                Avoid.LOGGER.error("@AutoFog requires the annotated type to extend Fog (avoid.net.fog)");
                return true;
            }

            var simpleName = klass.getSimpleName();

            suffixRemover:
            {
                if (!simpleName.endsWith("Fog")) {
                    Avoid.LOGGER.warn("All fog classes should end with 'Fog', found non-matching: {} ({})", simpleName, className);
                    break suffixRemover;
                }

                simpleName = simpleName.substring(0, simpleName.length() - 3);
            }

            var constructor = klass.getDeclaredConstructor();
            var idstr = id + ":" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);

            Avoid.LOGGER.debug("Registering fog '{}' (provisionary ID)", idstr);

            try {
                Fogs.register((Fog) constructor.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Failed to construct fog (%s)".formatted(idstr), e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        return false;
    }
}
