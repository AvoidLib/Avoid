package pl.olafcio.avoid.mods.loader.mod;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.mods.annotation_processor.OverwriteScreen;
import pl.olafcio.avoid.net.screen.Screen;
import pl.olafcio.avoid.net.screen.Screens;

@ApiStatus.Internal
public non-sealed interface LXScreenOverwrite
                  extends LX
{
    default boolean registerScreenOverwrite(Class<?> klass, String className) {
        if (klass.isAnnotationPresent(OverwriteScreen.class)) {
            if (!Screen.class.isAssignableFrom(klass)) {
                Avoid.LOGGER.error("@OverwriteScreen requires the annotated type to extend Screen (avoid.net.screen)");
                return true;
            }

            if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT) {
                Avoid.LOGGER.debug("@OverwriteScreen({}) on server, skipping", klass.getDeclaredAnnotation(OverwriteScreen.class).value().name());
                return true;
            }

            Avoid.LOGGER.debug("Scheduling screen overwrite: {} ({})", className, mod().getFileName().toString());

            Avoid.INSTANCE.Schedule(() -> {
                //noinspection unchecked
                Screens.overwrite(
                        klass.getDeclaredAnnotation(OverwriteScreen.class).value(),
                        (Class<? extends Screen>) klass
                );
            });
        }

        return false;
    }
}
