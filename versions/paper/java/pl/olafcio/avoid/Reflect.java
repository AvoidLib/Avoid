package pl.olafcio.avoid;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;

public final class Reflect {
    private Reflect() {}

    public static void call(Class<?> klass, String name, Object parent, Class<?>[] args, Object[] argv) {
        try {
            var m = klass.getDeclaredMethod(name, args);
            m.setAccessible(true);
            m.invoke(parent, argv);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("AvoidLib failed to reflectively invoke method '%s#%s'".formatted(klass.getName(), name), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T callx(Class<?> klass, String name, Object parent, Class<?>[] args, Object... argv) {
        try {
            var m = klass.getDeclaredMethod(name, args);
            m.setAccessible(true);
            return (T) m.invoke(parent, argv);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("AvoidLib failed to reflectively invoke method '%s#%s'".formatted(klass.getName(), name), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T callR(Class<?> klass, String name, Object parent, Class<?>[] args, Object[] argv) {
        try {
            var m = klass.getDeclaredMethod(name, args);
            m.setAccessible(true);
            return (T) m.invoke(parent, argv);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("AvoidLib failed to reflectively invoke method '%s#%s'".formatted(klass.getName(), name), e);
        }
    }

    public static <T> T construct(Class<T> klass, Class<?>[] args, Object[] argv) {
        try {
            var c = klass.getDeclaredConstructor(args);
            c.setAccessible(true);
            return c.newInstance(argv);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            throw new RuntimeException("AvoidLib failed to reflectively construct '%s'".formatted(klass.getName()), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<?> klass, String name, Class<T> type) {
        try {
            return (T) MethodHandles.privateLookupIn(klass, MethodHandles.lookup()).findVarHandle(klass, name, type).get();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException("AvoidLib failed to reflectively get '%s'".formatted(klass.getName()), e);
        }
    }

    public static void set(Class<?> klass, String name, Object parent, Object value) {
        try {
            var f = klass.getDeclaredField(name);
            f.setAccessible(true);
            f.set(parent, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException("AvoidLib failed to reflectively set '%s'".formatted(klass.getName()), e);
        }
    }
}
