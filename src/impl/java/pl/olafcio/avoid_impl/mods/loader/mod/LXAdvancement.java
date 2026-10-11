package pl.olafcio.avoid_impl.mods.loader.mod;

import com.google.common.base.CaseFormat;
import com.google.common.base.Supplier;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.api.Avoid;
import pl.olafcio.avoid.mods.annotation_processor.AutoAdvancement;
import pl.olafcio.avoid.mods.annotation_processor.AutoCustomPayload;
import pl.olafcio.avoid.mods.annotation_processor.AutoID;
import pl.olafcio.avoid.net.advancement.Advancement;
import pl.olafcio.avoid.net.advancement.Advancements;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.payload.CustomPayload;
import pl.olafcio.avoid.net.payload.Networking;
import pl.olafcio.avoid_impl.mods.loader.ModLoad;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;

@ApiStatus.Internal
public interface LXAdvancement {
    default boolean registerAutoAdvancement(String id, Class<?> klass, String className, ModLoad.ClassStatus usedAutoID)
            throws NoSuchMethodException
    {
        if (klass.isAnnotationPresent(AutoAdvancement.class)) {
            if (!Advancement.class.isAssignableFrom(klass)) {
                Avoid.LOGGER.error("@AutoAdvancement requires the annotated type to extend Advancement (avoid.net.advancement)");
                return true;
            }

            if (!klass.isAnnotationPresent(AutoID.class)) {
                Avoid.LOGGER.error("@AutoAdvancement requires the annotated type to be also annotated with @AutoID");
                return true;
            }

            usedAutoID.autoID = true;

            var simpleName = klass.getSimpleName();

            suffixRemover:
            {
                if (!simpleName.endsWith("Advancement")) {
                    Avoid.LOGGER.warn("All advancement classes should end with 'Advancement', found non-matching: {} ({})", simpleName, className);
                    break suffixRemover;
                }

                simpleName = simpleName.substring(0, simpleName.length() - 11);
            }

            var idstr = id + ":" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);

            Avoid.LOGGER.debug("Registering advancement '{}'", idstr);

            Advancements.register(Identification.of(idstr), (Class<? extends Advancement>) klass);
        }

        return false;
    }
}
