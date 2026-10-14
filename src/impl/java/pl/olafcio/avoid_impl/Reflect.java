package pl.olafcio.avoid_impl;

import org.jetbrains.annotations.ApiStatus;
import sun.misc.Unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;

@ApiStatus.Internal
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

    public static <T> T constructx(Class<T> klass, Class<?>[] args, Object... argv) {
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
            return (T) MethodHandles.privateLookupIn(klass, MethodHandles.lookup()).findStaticVarHandle(klass, name, type).get();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException("AvoidLib failed to reflectively get '%s'".formatted(klass.getName()), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<?> klass, String name, Class<T> type, Object instance) {
        try {
            return (T) MethodHandles.privateLookupIn(klass, MethodHandles.lookup()).findVarHandle(klass, name, type).get(instance);
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

    public static final Unsafe unsafe;
    public static final MethodHandles.Lookup IMPL_LOOKUP;

    static {
        var unsafeCls = Unsafe.class;
        try {
            var field = unsafeCls.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(unsafeCls);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try {
            var f = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            var base = unsafe.staticFieldBase(f);
            var offset = unsafe.staticFieldOffset(f);

            IMPL_LOOKUP = (MethodHandles.Lookup) unsafe.getObject(base, offset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setUnsafe(Class<?> klass, String name, Object value) {
        try {
            var field = klass.getDeclaredField(name);

            //FIXME unsafe is very bad (it's going to get removed) but it seems to be the only runtime-only way
            unsafe.putObject(
                    unsafe.staticFieldBase(field),
                    unsafe.staticFieldOffset(field),
                    value
            );
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("AvoidLib failed to unsafely set '%s'".formatted(klass.getName()), e);
        }
    }
}
