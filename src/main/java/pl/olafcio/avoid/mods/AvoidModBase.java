package pl.olafcio.avoid.mods;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.mods.resource.InvalidResourceException;
import pl.olafcio.avoid.mods.resource.UserResourceException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ApiStatus.Experimental
public abstract sealed class AvoidModBase
       permits AvoidMod
{
    AvoidModBase() {}

    @SuppressWarnings("unused")
    private AvoidModMeta meta;

    @ApiStatus.Experimental
    public final AvoidModMeta getMeta() {
        return meta;
    }

    @ApiStatus.Experimental
    public final void createDirectory() {
        _createDirectory();
    }

    @ApiStatus.Experimental
    public final Path getDirectory() {
        return _createDirectory();
    }

    private Path _createDirectory() {
        try {
            var path = AvoidWrappedLoader.getGameDir()
                                         .resolve("config")
                                         .resolve(meta.id());

            Files.createDirectories(path);

            return path;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create mod config directory: '%s' (%s)".formatted(meta.id(), meta.name()), e);
        }
    }

    @ApiStatus.Experimental
    public final byte[] getResource(String path)
           throws InvalidResourceException
    {
        try (var stream = this.getClass().getResourceAsStream(path)) {
            if (stream == null)
                throw new InvalidResourceException("Resource '%s' not found".formatted(path));

            return stream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiStatus.Experimental
    public final byte[] getConfigurableResource(String path)
           throws InvalidResourceException, UserResourceException
    {
        var dir = _createDirectory();
        var file = dir.resolve(path);

        if (Files.isRegularFile(file)) {
            try                   { return Files.readAllBytes(file);                                                        }
            catch (IOException e) { throw new UserResourceException("Resource file '%s' couldn't be read".formatted(path)); }
        }

        return getResource(path);
    }
}
