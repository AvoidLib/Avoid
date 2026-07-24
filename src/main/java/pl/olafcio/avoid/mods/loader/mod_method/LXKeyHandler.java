package pl.olafcio.avoid.mods.loader.mod_method;

import com.google.common.base.Function;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.mods.annotation_processor.KeyHandler;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.keyboard.bind.Category;
import pl.olafcio.avoid.net.keyboard.bind.Keybinds;
import pl.olafcio.avoid.net.keyboard.event.ClientKeyEvent;
import pl.olafcio.avoid.net.keyboard.event.ClientKeyPressEvent;
import pl.olafcio.avoid.net.keyboard.event.ClientKeyReleaseEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public interface LXKeyHandler {
    default void registerKeyHandler(Class<?> klass, Method m) {
        var name = klass.getName() + "::" + m.getName();

        if (!Modifier.isStatic(m.getModifiers())) {
            Avoid.LOGGER.warn("@KeyHandler not applicable to non-static method ({})", name);
            return;
        }

        m.setAccessible(true);

        Avoid.LOGGER.debug("Registering keyhandler '{}'", name);

        var annotation = m.getAnnotation(KeyHandler.class);

        var key = annotation.value();
        var trigger = annotation.trigger() == KeyHandler.Trigger.PRESS
                ? ClientKeyPressEvent.class
                : ClientKeyReleaseEvent.class;

        final var isHeld = getHeldSupplier(annotation, key);

        if (m.getParameterCount() == 0)
            EventManager.register(trigger, event -> {
                if (isHeld.apply(event)) {
                    try {
                        m.invoke(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to call keyhandler (%s)".formatted(name), e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        else if (m.getParameterCount() == 1 && m.getParameters()[0].getType() == trigger)
            EventManager.register(trigger, event -> {
                if (isHeld.apply(event)) {
                    try {
                        m.invoke(null, event);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to call keyhandler (%s)".formatted(name), e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        else
            Avoid.LOGGER.error("Failed to understand keyhandler's parameters ('{}'): {}", name, m.getParameters());
    }

    private static @NotNull Function<ClientKeyEvent, @NotNull Boolean> getHeldSupplier(KeyHandler annotation, int key) {
        var displayName = annotation.displayName();
        if (!displayName.isEmpty()) {
            var in = annotation.category();
            var out = in == KeyHandler.Category.MOVEMENT    ? Category.MOVEMENT    :
                      in == KeyHandler.Category.MISC        ? Category.MISC        :
                      in == KeyHandler.Category.MULTIPLAYER ? Category.MULTIPLAYER :
                      in == KeyHandler.Category.GAMEPLAY    ? Category.GAMEPLAY    :
                      in == KeyHandler.Category.INVENTORY   ? Category.INVENTORY   :
                      in == KeyHandler.Category.CREATIVE    ? Category.CREATIVE    :
                      in == KeyHandler.Category.SPECTATOR   ? Category.SPECTATOR   :
                      in == KeyHandler.Category.DEBUG       ? Category.DEBUG       :
                                                              null;

            if (out == null) {
                Avoid.LOGGER.error("Cannot register KeyHandler#displayName() without setting the category");
            } else {
                var supplier = Keybinds.register(displayName, key, out);
                return event -> supplier.get();
            }
        } else if (annotation.category() != KeyHandler.Category.__NOT_SET__) {
            Avoid.LOGGER.error("Cannot register KeyHandler#category() without setting the display name");
        }

        return event -> event.getKey() == key;
    }
}
