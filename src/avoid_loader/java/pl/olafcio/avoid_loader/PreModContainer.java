package pl.olafcio.avoid_loader;

import com.google.gson.JsonObject;

import java.nio.file.Path;
import java.util.List;

@Deprecated(forRemoval = true)
public final class PreModContainer {
    private final Path path;
    private final JsonObject manifest;

    public PreModContainer(Path path, JsonObject manifest) {
        this.path = path;
        this.manifest = manifest;
    }

    public Path path() {
        return path;
    }

    public JsonObject manifest() {
        return manifest;
    }

    public List<String> packages
         = List.of();

    @Override
    public String toString() {
        return "PreModContainer[" +
                "path=" + path + ", " +
                "manifest=" + manifest + ']';
    }
}
