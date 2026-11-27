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

    /**
     * Returns whether the modfile should have any classes loaded.
     * <br/><br/>
     * This is useful for e.g. distributing Avoid itself.<br/>
     * The Avoid JAR contains a {@code avoid.mod.json} to include its builtin extensions, while also using the underlying platform's format for the rest.
     * <br/><br/>
     * Essentially, lowcode allows you to make JAR-s that <i><b>may</b> have code</i>, but don't run it.<br/>
     * Lowcode is useful in the following situations:
     * <ol>
     *     <li>to create a mod collection;</li>
     *     <li>to create a datapack packaged as a mod;</li>
     *     <li>to create a resource pack packaged as a mod.</li>
     * </ol>
     * <p>
     * To make a lowcode JAR, append the {@code Lowcode: true} attribute to its manifest.
     */
    default boolean isLowcode() {
        return false;
    }

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
