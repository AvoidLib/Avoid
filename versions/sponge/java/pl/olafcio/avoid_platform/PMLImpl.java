package pl.olafcio.avoid_platform;

import pl.olafcio.avoid_loader.subloaders.ITransformer;

import java.nio.file.Path;
import java.util.ArrayList;

public final class PMLImpl {
    private PMLImpl() {}

    public static final ArrayList<ITransformer> transformers
                  = new ArrayList<>();

    public static void addToClasspath(Path path) {
        throw new RuntimeException("[PreModLoader#addToClasspath] This is not implemented on Sponge");
    }

    public static void addTransformer(ITransformer transformer) {
        transformers.add(transformer);
    }
}
