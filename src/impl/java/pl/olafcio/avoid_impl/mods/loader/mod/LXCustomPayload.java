package pl.olafcio.avoid_impl.mods.loader.mod;

import com.google.common.base.CaseFormat;
import com.google.common.base.Supplier;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.api.Avoid;
import pl.olafcio.avoid.mods.annotation_processor.AutoCustomPayload;
import pl.olafcio.avoid.mods.annotation_processor.AutoID;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.payload.CustomPayload;
import pl.olafcio.avoid.net.payload.Networking;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;

@ApiStatus.Internal
public interface LXCustomPayload {
    default boolean registerAutoCustomPayload(String id, Class<?> klass, String className, AtomicBoolean usedAutoID)
            throws NoSuchMethodException
    {
        if (klass.isAnnotationPresent(AutoCustomPayload.class)) {
            if (!CustomPayload.class.isAssignableFrom(klass)) {
                Avoid.LOGGER.error("@AutoCustomPayload requires the annotated type to extend CustomPayload (avoid.net.payload)");
                return true;
            }

            if (!klass.isAnnotationPresent(AutoID.class)) {
                Avoid.LOGGER.error("@AutoCustomPayload requires the annotated type to be also annotated with @AutoID");
                return true;
            }

            usedAutoID.set(true);

            var simpleName = klass.getSimpleName();

            suffixRemover:
            {
                if (!simpleName.endsWith("Payload")) {
                    Avoid.LOGGER.warn("All custom payload classes should end with 'Payload', found non-matching: {} ({})", simpleName, className);
                    break suffixRemover;
                }

                simpleName = simpleName.substring(0, simpleName.length() - 7);
            }

            var constructor = klass.getDeclaredConstructor();
            var idstr = id + ":" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);

            Avoid.LOGGER.debug("Registering custom payload '{}'", idstr);

            registerUnsafe(Identification.of(idstr), () -> {
                try {
                    return (pl.olafcio.avoid.net.payload.CustomPayload) constructor.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Failed to construct custom payload (%s)".formatted(idstr), e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }, (Class<? extends pl.olafcio.avoid.net.payload.CustomPayload>) klass);
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private <T extends CustomPayload> void registerUnsafe(Identification id, Supplier<? extends CustomPayload> supplier, Class<? extends CustomPayload> clazz) {
        Networking.register(id, (Supplier<T>) supplier, (Class<T>) clazz);
    }
}
