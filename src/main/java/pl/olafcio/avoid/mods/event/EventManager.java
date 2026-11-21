package pl.olafcio.avoid.mods.event;

import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
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
        var array = listeners.get(event.getClass());
        if (array != null) {
            for (Consumer<?> el : array)
                ((Consumer<T>) el).accept(event);
        }
    }

    @SuppressWarnings("Java8MapApi")
    public static <T> void register(Class<T> event, Consumer<T> handler) {
        var value = listeners.get(event);
        if (value == null)
            listeners.put(event, value = new ArrayList<>());

        value.add(handler);
    }

    public static void collect(Class<?> klass, Method[] methods) {
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

                boolean ignoreCancelled = m.getAnnotation(EventHandler.class)
                                           .ignoreCancelled();

                var actuallyCancellable = Cancellable.class.isAssignableFrom(klass);
                if (ignoreCancelled && !actuallyCancellable)
                    LOGGER.debug("@EventHandler(ignoreCancelled = true) on a non-cancellable event ({}#{})", className, methodName);

                final var ignoreForReal = ignoreCancelled && actuallyCancellable;
                final MethodHandle handle;

                try {
                    handle = MethodHandles.lookup().unreflect(m);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                register(type, obj -> {
                    try {
                        if (ignoreForReal && ((Cancellable) obj).isCancelled())
                            return;

                        handle.invoke(obj);
                    } catch (Throwable e) {
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

                boolean ignoreCancelled = m.getAnnotation(EventHandler.class)
                                           .ignoreCancelled();

                var actuallyCancellable = Cancellable.class.isAssignableFrom(klass);
                if (ignoreCancelled && !actuallyCancellable)
                    LOGGER.debug("@EventHandler(ignoreCancelled = true) on a non-cancellable event ({}#{})", className, methodName);

                final var ignoreForReal = ignoreCancelled && actuallyCancellable;
                final MethodHandle handle;

                try {
                    handle = MethodHandles.lookup().unreflect(m);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                register(type, obj -> {
                    try {
                        if (ignoreForReal && ((Cancellable) obj).isCancelled())
                            return;

                        handle.invoke(instance, obj);
                    } catch (Throwable e) {
                        LOGGER.error("Failed to invoke {} for {}#{}", typeName, className, methodName);
                    }
                });
            }
        }
    }
}
