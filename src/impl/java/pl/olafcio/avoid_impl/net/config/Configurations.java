package pl.olafcio.avoid_impl.net.config;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.config.AutoConfiguration;
import pl.olafcio.avoid.net.config.ConfigState;
import pl.olafcio.avoid.net.config.annotations.Nested;
import pl.olafcio.avoid.net.config.annotations.YAML;
import pl.olafcio.avoid_common.Either;
import pl.olafcio.avoid_impl.net.config.controller.ConfigController;
import pl.olafcio.avoid_impl.net.config.controller.ConfigControllers;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

@ApiStatus.Experimental
public final class Configurations {
    @ApiStatus.Internal
    private Configurations() {}

    public static final HashMap<Class<?>, ConfigInstance<?>> CONFIGURATIONS
                  = new HashMap<>();

    public static <T extends AutoConfiguration> void addConfiguration(Path path, Supplier<T> constructor) {
        var config = constructor.get();
        var klass = config.getClass();

        var controllers = collectControllers(klass);
        var instance = new ConfigInstance<>(path, config, controllers);

        CONFIGURATIONS.put(config.getClass(), instance);

        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            throw new RuntimeException("[Configurations#addConfiguration] Failed to create parent directories of path: '%s'".formatted(path.toAbsolutePath()), e);
        }

        instance.load();
        instance.save();
    }

    @NotNull
    private static ArrayList<Either<ConfigController<?, ?, ?>, ConfigNest>> collectControllers(Class<?> klass) {
        var controllers = new ArrayList<Either<ConfigController<?,?,?>, ConfigNest>>();

        while (klass != Object.class) {
            var fields = klass.getDeclaredFields();

            for (var field : fields) {
                if (field.isAnnotationPresent(YAML.class)) {
                    field.setAccessible(true);

                    controllers.add(Either.left(ConfigControllers.convert(field)));
                } else if (field.isAnnotationPresent(Nested.class)) {
                    field.setAccessible(true);

                    VarHandle varHandle;

                    try {
                        varHandle = MethodHandles.privateLookupIn(field.getDeclaringClass(), MethodHandles.lookup())
                                                 .unreflectVarHandle(field);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to get varhandle of configuration field '%s' on config class '%s'".formatted(field.getName(), field.getDeclaringClass().getName()), e);
                    }

                    controllers.add(Either.right(new ConfigNest(field.getName(), collectControllers(field.getType()), field, varHandle)));
                }
            }

            klass = klass.getSuperclass();
        }

        return controllers;
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> configClass) {
        return (T) CONFIGURATIONS.get(configClass).instance();
    }

    public static <T> ConfigState<T> getState(Class<T> configClass) {
        var value = CONFIGURATIONS.get(configClass);

        return new ConfigState<>() {
            @SuppressWarnings("unchecked")
            @Override
            public T getInstance() {
                return (T) value.instance();
            }

            @Override
            public void reload() {
                value.load();
            }

            @Override
            public void save() {
                value.save();
            }
        };
    }
}
