package pl.olafcio.avoid_loader;

import com.google.gson.JsonObject;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Deprecated(forRemoval = true)
public final class PreModContainer {
    private final Path path;
    private final JsonObject manifest;
    private final ModClassLoader classLoader;
    private final PreModContainer parent;
    private final ArrayList<PreModContainer> children;
    private final String additionalText;

    PreModContainer(Path path, JsonObject manifest, ModClassLoader classLoader, PreModContainer parent, ArrayList<PreModContainer> children) {
        this.path = path;
        this.manifest = manifest;
        this.classLoader = classLoader;
        this.parent = parent;
        this.children = children;
        this.additionalText = null;
    }

    PreModContainer(Path path, JsonObject manifest, ModClassLoader classLoader, PreModContainer parent) {
        this.path = path;
        this.manifest = manifest;
        this.classLoader = classLoader;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.additionalText = null;
    }

    PreModContainer(Path path, JsonObject manifest, ModClassLoader classLoader, PreModContainer parent, ArrayList<PreModContainer> children, String additionalText) {
        this.path = path;
        this.manifest = manifest;
        this.classLoader = classLoader;
        this.parent = parent;
        this.children = children;
        this.additionalText = additionalText;
    }

    PreModContainer(Path path, JsonObject manifest, ModClassLoader classLoader, PreModContainer parent, String additionalText) {
        this.path = path;
        this.manifest = manifest;
        this.classLoader = classLoader;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.additionalText = additionalText;
    }

    public Path path() {
        return path;
    }
    public JsonObject manifest() {
        return manifest;
    }
    public ModClassLoader classLoader() {
        return classLoader;
    }
    public PreModContainer parent() {
        return parent;
    }
    public List<PreModContainer> children() {
        return children;
    }
    public Optional<String> additionalText() {
        return Optional.ofNullable(additionalText);
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
