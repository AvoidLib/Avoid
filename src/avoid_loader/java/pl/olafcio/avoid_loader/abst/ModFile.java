package pl.olafcio.avoid_loader.abst;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid_loader.ModClassLoader;
import pl.olafcio.avoid_loader.interfaces.IModFile;
import pl.olafcio.reflectstorm.ReflectStorm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ModFile implements IModFile {
    private final Path path;
    private       JarFile jar;
    private final ModClassLoader classLoader;

    private final Path actualPath;
    private boolean lowcode = false;

    @Override
    public boolean isLowcode() {
        return lowcode;
    }

    public ModFile(Path path) {
        try {
            this.path = path;

            // Just temporary!
            try (var jar = new JarFile(path.toFile())) {
                var manifest = jar.getManifest();
                if (manifest != null) {
                    this.lowcode = Objects.equals(manifest.getMainAttributes().getValue("Lowcode"), "true");
                }
            }

            if (lowcode) {
                this.classLoader = new ModClassLoader(new URL[0], this.getClass().getClassLoader());
                this.actualPath = path;
                this.jar = null;
            } else {
                this.classLoader = new ModClassLoader(new URL[]{ path.toUri().toURL() }, this.getClass().getClassLoader());
                this.jar = new JarFile((actualPath = Path.of(classLoader.getURLs()[0].toURI())).toFile());  // todo fix quality of code
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("[ModFile::constructor] Failed to create ModClassLoader for: '%s'".formatted(path.toAbsolutePath()), e);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("[ModFile::constructor] Failed to create JarFile for: '%s'".formatted(path.toAbsolutePath()), e);
        }
    }

    public ModFile(Path path, ModClassLoader classLoader) {
        try {
            this.path = path;
            this.classLoader = classLoader;
            //TODO Make this code better
            this.jar = new JarFile((actualPath = (classLoader.getURLs().length == 0 ? path : Path.of(classLoader.getURLs()[0].toURI()))).toFile());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("[ModFile::constructor] Failed to create JarFile for: '%s'".formatted(path.toAbsolutePath()), e);
        }
    }

    private ModFile(Path directory, ModClassLoader classLoader, Void _useless) {
        this.path = directory;
        this.actualPath = directory;
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
        if (jar == null)
            return null;

        var entry = jar.getJarEntry(path);
        if (entry == null)
            return null;

        return jar.getInputStream(entry);
    }

    private static final Class<?> URLClassPath;
    private static final Class<?> JarLoader;

    static {
        try {
            URLClassPath = Class.forName("jdk.internal.loader.URLClassPath");
            JarLoader    = Class.forName("jdk.internal.loader.URLClassPath$JarLoader");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final ReflectStorm.Field URLClassLoader_ucp
                       = ReflectStorm.builder()
                                     .klass(URLClassLoader.class)
                                     .field("ucp", URLClassPath);

    private static final ReflectStorm.Field URLClassPath_loaders
                       = ReflectStorm.builder()
                                     .klass(URLClassPath)
                                     .field("loaders", ArrayList.class);

    private static final ReflectStorm.Field JarLoader_jar
                       = ReflectStorm.builder()
                                     .klass(JarLoader)
                                     .field("jar", JarFile.class);

    private static final ReflectStorm.Method JarLoader_ensureOpen
                       = ReflectStorm.builder()
                                     .klass(JarLoader)
                                     .method("ensureOpen");

    private static final Path tmp = Path.of(".cache/avoid_loader/.tmp");
    static {
        try {
            Files.createDirectories(tmp);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create '.cache/avoid_loader/.tmp' directory", e);
        }
    }

    @Override
    public void addResource(String path, byte[] data) throws IOException {
        //= Dir
        if (jar == null) {
            Files.write(this.path.resolve(path), data);
        }

        //= Jar
        if (jar != null) {
            if (jar.getEntry(path) != null) {
                return;
            }

            var jarLoaders = new ArrayList<>();

            try {
                // close
                for (var loader : (ArrayList<?>) URLClassPath_loaders.get(URLClassLoader_ucp.get(classLoader))) {
                    if (JarLoader.isInstance(loader)) {
                        ((JarFile) JarLoader_jar.get(loader)).close();

                        jarLoaders.add(loader);
                    }
                }

                // modify
                var newPath = tmp.resolve(actualPath.getFileName().toString() + "-modified.jar");

                try (
                        var fos = new FileOutputStream(newPath.toFile());
                        var zos = new ZipOutputStream(fos);
                ) {
                    var entries = jar.entries();

                    while (entries.hasMoreElements()) {
                        var entry = entries.nextElement();

                        try (var stream = jar.getInputStream(entry)) {
                            zos.putNextEntry(entry);
                            stream.transferTo(zos);
                        }
                    }

                    zos.putNextEntry(new ZipEntry(path));
                    zos.write(data);
                }

                jar.close();

                Files.move(newPath, actualPath, StandardCopyOption.REPLACE_EXISTING);

                this.jar = new JarFile(actualPath.toFile());
            } finally {
                // reopen
                var ex = new RuntimeException("Failed to reopen jarLoaders");

                for (var loader : jarLoaders) {
                    try {
                        JarLoader_jar.set(loader, null);
                        JarLoader_ensureOpen.invoke(loader);
                    } catch (InvocationTargetException e) {
                        ex.addSuppressed(e.getCause());
                    }
                }

                if (ex.getSuppressed().length != 0)
                    throw ex;
            }
        }
    }
}
