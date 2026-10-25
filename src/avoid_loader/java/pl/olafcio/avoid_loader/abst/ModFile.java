package pl.olafcio.avoid_loader.abst;

import pl.olafcio.avoid_loader.ModClassLoader;
import pl.olafcio.avoid_loader.interfaces.IModFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

public class ModFile implements IModFile {
    private final Path path;
    private final ModClassLoader classLoader;

    public ModFile(Path path) {
        try {
            this.path = path;
            this.classLoader = new ModClassLoader(new URL[]{ path.toUri().toURL() }, this.getClass().getClassLoader());
        } catch (MalformedURLException e) {
            throw new RuntimeException("[ModFile::constructor] Failed to create ModClassLoader for: '%s'".formatted(path.toAbsolutePath()), e);
        }
    }

    public ModFile(Path path, ModClassLoader classLoader) {
        this.path = path;
        this.classLoader = classLoader;
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public ModClassLoader getClassLoader() {
        return classLoader;
    }
}
