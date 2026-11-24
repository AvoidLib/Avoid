package pl.olafcio.avoid_loader;

import org.apache.commons.lang3.function.FailableFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@ApiStatus.Experimental
public abstract sealed class ClassPatcher implements AutoCloseable permits ClassPatcher.Dir, ClassPatcher.Jar, ClassPatcher.Noop {
    private ClassPatcher() {}

    public abstract void patch(FailableFunction<InputStream, byte[], IOException> consumer) throws IOException;
    public abstract void close() throws IOException;

    private static final Path PATCHED_JARS = Path.of(".cache/avoid_loader/patched_jars/");
    static {
        try {
            Files.createDirectories(PATCHED_JARS);
        } catch (IOException e) {
            throw new RuntimeException("ClassPatcher had a pee please fix me", e);
        }
    }

    public static ClassPatcher create(URL url) throws IOException, URISyntaxException {
        var path = Path.of(url.toURI());

        if (Files.isDirectory(path)) {
            return new Dir(path);
        } else if (Files.exists(path)) {
            return new Jar(path);
        } else {
            // No-op?
            return new Noop(path);
        }
    }

    protected Path output;
    public final Path getPath() {
        return output;
    }

    private static final class Jar extends ClassPatcher {
        private final Path urlpath;
        private final FileOutputStream fos;
        private final ZipOutputStream zos;

        private Jar(Path urlpath) throws IOException {
            this.urlpath = urlpath;

            fos = new FileOutputStream((output = PATCHED_JARS.resolve(urlpath.getFileName().toString())).toFile());
            zos = new ZipOutputStream(fos);
        }

        @Override
        public void patch(FailableFunction<InputStream, byte[], IOException> consumer) throws IOException {
            try (var zip = new ZipFile(urlpath.toFile())) {
                var entries = zip.entries();

                while (entries.hasMoreElements()) {
                    var entry = entries.nextElement();

                    if (entry.getName().endsWith(".class")) {
                        try (var is = zip.getInputStream(entry)) {
                            var bytes = consumer.apply(is);

                            zos.putNextEntry(entry);
                            zos.write(bytes);
                        }
                    } else {
                        zos.putNextEntry(entry);

                        try (var is = zip.getInputStream(entry)) {
                            is.transferTo(zos);
                        }
                    }
                }
            }

            close();
        }

        @Override
        public void close() throws IOException {
            zos.close();
            fos.close();
        }
    }

    private static final class Dir extends ClassPatcher {
        private final Path urlpath;

        private Dir(Path urlpath) {
            this.urlpath = urlpath;
            this.output = urlpath;
        }

        @Override
        public void patch(FailableFunction<InputStream, byte[], IOException> consumer) throws IOException {
            Files.walkFileTree(urlpath, new FileVisitor<>() {
                @Override
                public @NotNull FileVisitResult preVisitDirectory(Path dir, @NotNull BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public @NotNull FileVisitResult visitFile(Path file, @NotNull BasicFileAttributes attrs) throws IOException {
                    if (!file.getFileName().toString().endsWith(".class"))
                        return FileVisitResult.CONTINUE;

                    byte[] bytes;

                    try (var is = new FileInputStream(file.toFile())) {
                        bytes = consumer.apply(is);
                    }

                    Files.write(file, bytes);

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public @NotNull FileVisitResult visitFileFailed(Path file, @NotNull IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public @NotNull FileVisitResult postVisitDirectory(Path dir, @Nullable IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        }

        @Override
        public void close() throws IOException {
            // No-op
        }
    }

    private static final class Noop extends ClassPatcher {
        public Noop(Path path) {
            this.output = path;
        }

        @Override
        public void patch(FailableFunction<InputStream, byte[], IOException> consumer) throws IOException {
            //
        }

        @Override
        public void close() throws IOException {
            //
        }
    }
}
