package pl.olafcio.avoid_loader.abst;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid_loader.ModClassLoader;
import pl.olafcio.avoid_loader.interfaces.IModFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
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

    private ModFile(Path directory, ModClassLoader classLoader, Void _useless) {
        this.path = directory;
        this.jar = null;
        this.classLoader = classLoader;
    }

    public static ModFile forDirectories(Path... directories) {
        return new ModFile(
                directories[0],

                new ModClassLoader(
                        Arrays.stream(directories)
                              .map(dir -> {
                                  try {
                                      return dir.toUri().toURL();
                                  } catch (MalformedURLException e) {
                                      throw new RuntimeException("[ModFile::forDirectories] Failed to create URL for: '%s'".formatted(dir), e);
                                  }
                              })
                              .toArray(URL[]::new),
                        ModFile.class.getClassLoader()),

                null
        ) {
            @Override
            @Nullable
            public InputStream getResourceAsStream(String path) throws IOException {
                for (var directory : directories) {
                    var file = directory.resolve(path).toFile();
                    if (file.exists())
                        return new FileInputStream(file);
                }

                return null;
            }
        };
    }

    @Override
    @NotNull
    public Path getPath() {
        return path;
    }

    @Override
    public JarFile getJar() {
        return jar;
    }

    @Override
    @NotNull
    public ModClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public @Nullable InputStream getResourceAsStream(String path) throws IOException {
        var entry = jar.getJarEntry(path);
        if (entry == null)
            return null;

        return jar.getInputStream(entry);
    }
}
