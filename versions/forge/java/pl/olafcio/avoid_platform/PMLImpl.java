package pl.olafcio.avoid_platform;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid_loader.subloaders.ITransformer;
import pl.olafcio.avoid_platform.Reflect;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;

@ApiStatus.Internal
public final class PMLImpl {
    private PMLImpl() {}

    public static final ArrayList<ITransformer> transformers
                  = new ArrayList<>();

    public static ArrayList<Path> classpath
            = new ArrayList<>();

    public static void addToClasspath(Path path) {
        if (classpath == null)
            throw new RuntimeException("[PreModLoader#addToClasspath] This cannot be used after early init on Forge");

        classpath.add(path);
    }

    public static void addTransformer(ITransformer transformer) {
        transformers.add(transformer);
    }
}