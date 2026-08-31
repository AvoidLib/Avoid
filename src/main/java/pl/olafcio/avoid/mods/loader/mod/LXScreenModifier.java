package pl.olafcio.avoid.mods.loader.mod;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.mods.annotation_processor.ModifyScreen;
import pl.olafcio.avoid.mods.annotation_processor.OverwriteScreen;
import pl.olafcio.avoid.net.screen.Screen;
import pl.olafcio.avoid.net.screen.Screens;
import pl.olafcio.avoid.net.screen_modifier.ScreenModifier;
import pl.olafcio.avoid.net.screen_modifier.ScreenModifiers;

import java.lang.reflect.InvocationTargetException;

@ApiStatus.Internal
public non-sealed interface LXScreenModifier
                  extends LX
{
    default boolean registerScreenModifier(Class<?> klass, String className) {
        if (klass.isAnnotationPresent(ModifyScreen.class)) {
            if (!ScreenModifier.class.isAssignableFrom(klass)) {
                Avoid.LOGGER.error("@ModifyScreen requires the annotated type to extend ScreenModifier (avoid.net.screen_modifier)");
                return true;
            }

            if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT) {
                Avoid.LOGGER.debug("@ModifyScreen({}) on server, skipping", klass.getDeclaredAnnotation(ModifyScreen.class).value().name());
                return true;
            }

            Avoid.LOGGER.debug("Scheduling screen modifier: {} ({})", className, mod().getFileName().toString());

            Avoid.INSTANCE.Schedule(() -> {
                ScreenModifiers.register(
                        () -> {
                            try {
                                return (ScreenModifier) klass.getDeclaredConstructor().newInstance();
                            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                                throw new RuntimeException("Failed to construct screen modifier (%s)".formatted(klass.getName()), e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        }
//                        , klass.getDeclaredAnnotation(ModifyScreen.class).value()
                        //FIXME
                );
            });
        }

        return false;
    }
}
