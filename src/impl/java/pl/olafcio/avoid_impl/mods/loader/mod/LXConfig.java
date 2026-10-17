package pl.olafcio.avoid_impl.mods.loader.mod;

import com.google.common.base.CaseFormat;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.api.Avoid;
import pl.olafcio.avoid.mods.annotation_processor.AutoConfig;
import pl.olafcio.avoid.mods.annotation_processor.AutoFilename;
import pl.olafcio.avoid.net.config.AutoConfiguration;
import pl.olafcio.avoid.net.config.Configurations;
import pl.olafcio.avoid_impl.mods.loader.ModLoad;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

@ApiStatus.Internal
public interface LXConfig {
    default boolean registerAutoConfig(String id, Class<?> klass, String className, ModLoad.ClassStatus usedAutoID)
            throws NoSuchMethodException
    {
        if (klass.isAnnotationPresent(AutoConfig.class)) {
            if (!AutoConfiguration.class.isAssignableFrom(klass)) {
                Avoid.LOGGER.error("@AutoConfig requires the annotated type to implement AutoConfiguration (avoid.net.config)");
                return true;
            }

            if (!klass.isAnnotationPresent(AutoFilename.class)) {
                Avoid.LOGGER.error("@AutoConfig requires the annotated type to be also annotated with @AutoFilename");
                return true;
            }

            usedAutoID.autoFilename = true;

            var simpleName = klass.getSimpleName();

            suffixRemover:
            {
                if (!simpleName.endsWith("Config")) {
                    Avoid.LOGGER.warn("All config classes should end with 'Config', found non-matching: {} ({})", simpleName, className);
                    break suffixRemover;
                }

                simpleName = simpleName.substring(0, simpleName.length() - 6);

                if (simpleName.isEmpty())
                    simpleName = "Config";
            }

            var path = Path.of("./configs").resolve(id).resolve(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName) + ".yml");
            var constructor = klass.getDeclaredConstructor();

            Avoid.LOGGER.debug("Registering config '{}'", path);

            Configurations.addConfiguration(path, () -> {
                try {
                    return (AutoConfiguration) constructor.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Failed to construct config (%s)".formatted(path), e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return false;
    }
}
