package pl.olafcio.avoid.mods.event;

import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

@ApiStatus.Experimental
public final class EventManager {
    @ApiStatus.Internal
    private EventManager() {}

    private static final Logger LOGGER
                       = LogUtils.getLogger();

    private static final HashMap<Class<?>, ArrayList<Consumer<?>>> listeners
                   = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> void fire(T event) {
        if (listeners.containsKey(event.getClass())) {
            var array = listeners.get(event.getClass());
            for (Consumer<?> el : array)
                ((Consumer<T>) el).accept(event);
        }
    }

    public static <T> void register(Class<T> event, Consumer<T> handler) {
        if (!listeners.containsKey(event))
            listeners.put(event, new ArrayList<>());

        listeners.get(event).add(handler);
    }

    public static void collect(Class<?> klass) {
        var methods = klass.getDeclaredMethods();
        for (Method m : methods) {
            if (Modifier.isStatic(m.getModifiers()) && m.isAnnotationPresent(EventHandler.class)) {
                String className = klass.getName();
                String methodName = m.getName();

                var params = m.getParameters();
                if (params.length != 1) {
                    LOGGER.error("@EventHandler method must have only 1 parameter ({}#{})", className, methodName);
                    continue;
                }

                m.setAccessible(true);

                var type = params[0].getType();
                var typeName = type.getSimpleName();

                register(type, obj -> {
                    try {
                        m.invoke(null, obj);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        LOGGER.error("Failed to invoke static {} for {}#{}", typeName, className, methodName);
                    }
                });
            }
        }
    }

    public static void collect(Class<?> klass, Object instance) {
        var methods = klass.getDeclaredMethods();
        for (Method m : methods) {
            if (!Modifier.isStatic(m.getModifiers()) && m.isAnnotationPresent(EventHandler.class)) {
                String className = klass.getName();
                String methodName = m.getName();

                var params = m.getParameters();
                if (params.length != 1) {
                    LOGGER.error("@EventHandler method must have only 1 parameter ({}#{})", className, methodName);
                    continue;
                }

                m.setAccessible(true);

                var type = params[0].getType();
                var typeName = type.getSimpleName();

                register(type, obj -> {
                    try {
                        m.invoke(instance, obj);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        LOGGER.error("Failed to invoke {} for {}#{}", typeName, className, methodName);
                    }
                });
            }
        }
    }
}
