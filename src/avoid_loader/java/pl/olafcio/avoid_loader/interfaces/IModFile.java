package pl.olafcio.avoid_loader.interfaces;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface IModFile {
    Path        getPath();
    ClassLoader getClassLoader();

    default String hash() {
        try (var stream = Files.newInputStream(getPath())) {
            return DigestUtils.sha1Hex(stream);
        } catch (IOException e) {
            throw new RuntimeException("[IModFile#hash] Failed to hash with SHA1", e);
        }
    }
}
