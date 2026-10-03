package pl.olafcio.avoid_bundled;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid_loader.subloaders.ITransformer;
import pl.olafcio.avoid_impl.Reflect;

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

    public static void addToClasspath(Path path) {
        var ucl = (URLClassLoader) PMLImpl.class.getClassLoader();

        try {
            Reflect.call(URLClassLoader.class, "addURL", ucl, new Class<?>[]{ URL.class }, new Object[]{
                    path.toUri().toURL()
            });
        } catch (MalformedURLException e) {
            throw new RuntimeException("[PreModLoader#addToClasspath] Failed adding '%s'  /MalformedURLException".formatted(path), e);
        }
    }

    public static void addTransformer(ITransformer transformer) {
        transformers.add(transformer);
    }
}
