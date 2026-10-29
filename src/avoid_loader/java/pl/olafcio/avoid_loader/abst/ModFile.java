package pl.olafcio.avoid_loader.abst;

import pl.olafcio.avoid_loader.ModClassLoader;
import pl.olafcio.avoid_loader.interfaces.IModFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.jar.JarFile;

public class ModFile implements IModFile {
    private final Path path;
    private final JarFile jar;
    private final ModClassLoader classLoader;

    public ModFile(Path path) {
        try {
            this.path = path;
            this.jar = new JarFile(path.toFile());
            this.classLoader = new ModClassLoader(new URL[]{ path.toUri().toURL() }, this.getClass().getClassLoader());
        } catch (MalformedURLException e) {
            throw new RuntimeException("[ModFile::constructor] Failed to create ModClassLoader for: '%s'".formatted(path.toAbsolutePath()), e);
        } catch (IOException e) {
            throw new RuntimeException("[ModFile::constructor] Failed to create JarFile for: '%s'".formatted(path.toAbsolutePath()), e);
        }
    }

    public ModFile(Path path, ModClassLoader classLoader) {
        try {
            this.path = path;
            this.jar = new JarFile(path.toFile());
            this.classLoader = classLoader;
        } catch (IOException e) {
            throw new RuntimeException("[ModFile::constructor] Failed to create JarFile for: '%s'".formatted(path.toAbsolutePath()), e);
        }
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public JarFile getJar() {
        return jar;
    }

    @Override
    public ModClassLoader getClassLoader() {
        return classLoader;
    }
}
