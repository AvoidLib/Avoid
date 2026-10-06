package pl.olafcio.avoid_platform;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid_loader.subloaders.ITransformer;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.List;

@ApiStatus.Internal
public final class PMLImpl {
    private PMLImpl() {}

    public static void addToClasspath(Path path, String... startupPackages) {
        throw new RuntimeException("[PreModLoader#addToClasspath] Not implemented on Paper");
    }

    public static void addTransformer(ITransformer transformer) {
        throw new RuntimeException("[PreModLoader#addToClasspath] Not implemented on Paper");
    }
}
