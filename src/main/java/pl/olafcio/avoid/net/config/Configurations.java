package pl.olafcio.avoid.net.config;

import org.jetbrains.annotations.ApiStatus;

import java.nio.file.Path;
import java.util.function.Supplier;

public final class Configurations {
    @ApiStatus.Internal
    private Configurations() {}

    public static void addConfiguration(Path path, Supplier<? extends AutoConfiguration> constructor) {
        pl.olafcio.avoid_impl.net.config.Configurations.addConfiguration(path, constructor);
    }

    public static <T> T get(Class<T> configClass) {
        return pl.olafcio.avoid_impl.net.config.Configurations.get(configClass);
    }

    public static <T> ConfigState<T> getState(Class<T> configClass) {
        return pl.olafcio.avoid_impl.net.config.Configurations.getState(configClass);
    }
}
