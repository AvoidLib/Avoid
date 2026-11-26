package pl.olafcio.avoid_loader.interfaces;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid_loader.ModClassLoader;
import pl.olafcio.avoid_platform.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarFile;

public interface IModFile {
    @NotNull  Path           getPath();
    @Nullable JarFile        getJar();
    @NotNull  ModClassLoader getClassLoader();

    @ApiStatus.Experimental
    @Nullable
    InputStream getResourceAsStream(String path) throws IOException;

    @ApiStatus.Experimental
    void addResource(String path, byte[] data) throws IOException;

    @NotNull
    default String hash() {
        try (var stream = Files.newInputStream(getPath())) {
            return DigestUtils.sha1Hex(stream);
        } catch (IOException e) {
            throw new RuntimeException("[IModFile#hash] Failed to hash with SHA1", e);
        }
    }
}
