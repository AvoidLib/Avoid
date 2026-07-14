package pl.olafcio.avoid.mods.loader.mod;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.mods.annotation_processor.AutoCommand;
import pl.olafcio.avoid.net.command.Command;
import pl.olafcio.avoid.net.command.CommandManager;

import java.lang.reflect.InvocationTargetException;

@ApiStatus.Internal
public interface LXCommand {
    default boolean registerAutoCommand(Class<?> klass)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        if (klass.isAnnotationPresent(AutoCommand.class)) {
            if (!Command.class.isAssignableFrom(klass)) {
                Avoid.LOGGER.error("@AutoCommand requires the annotated type to extend Command (avoid.net.command)");
                return true;
            }

            CommandManager.add((Command) klass.getDeclaredConstructor().newInstance());
        }

        return false;
    }
}
