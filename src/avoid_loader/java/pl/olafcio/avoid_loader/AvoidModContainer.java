package pl.olafcio.avoid_loader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid_loader.abst.ModFile;
import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.services.IModLoader;
import pl.olafcio.avoid_loader.interfaces.IModMetadata;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Deprecated(forRemoval = true)
public class AvoidModContainer implements IMod {
    private final ModFile modFile;
    private final IModLoader origin;
    private final JsonObject manifest;
    private final AvoidModContainer parent;
    private final ArrayList<IMod> children;
    private       String additionalText;

    public AvoidModContainer(ModFile modFile, IModLoader origin, JsonObject manifest, AvoidModContainer parent, ArrayList<IMod> children) {
        this.modFile = modFile;
        this.origin = origin;
        this.manifest = manifest;
        this.parent = parent;
        this.children = children;
        this.additionalText = null;
    }

    public AvoidModContainer(ModFile modFile, IModLoader origin, JsonObject manifest, AvoidModContainer parent) {
        this.modFile = modFile;
        this.origin = origin;
        this.manifest = manifest;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.additionalText = null;
    }

    public AvoidModContainer(ModFile modFile, IModLoader origin, JsonObject manifest, AvoidModContainer parent, ArrayList<IMod> children, String additionalText) {
        this.modFile = modFile;
        this.origin = origin;
        this.manifest = manifest;
        this.parent = parent;
        this.children = children;
        this.additionalText = additionalText;
    }

    public AvoidModContainer(ModFile modFile, IModLoader origin, JsonObject manifest, AvoidModContainer parent, String additionalText) {
        this.modFile = modFile;
        this.origin = origin;
        this.manifest = manifest;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.additionalText = additionalText;
    }

    @Override
    public ModFile getModFile() {
        return modFile;
    }

    @Override
    public IModLoader getModLoader() {
        return origin;
    }

    public Path path() {
        return modFile.getPath();
    }
    public JsonObject manifest() {
        return manifest;
    }
    public ModClassLoader classLoader() {
        return modFile.getClassLoader();
    }
    public AvoidModContainer parent() {
        return parent;
    }
    public List<IMod> children() {
        return children;
    }
    public Optional<String> additionalText() {
        return Optional.ofNullable(additionalText);
    }

    public void setAdditionalText(String additionalText) {
        this.additionalText = additionalText;
    }

    public List<String> packages
         = List.of();

    @Override
    public String toString() {
        return "PreModContainer[" +
                "path=" + path() + ", " +
                "manifest=" + manifest + ']';
    }

    @Override
    public String toListing() {
        return manifest().get("name").getAsString() + " " +
               manifest().get("version").getAsString() + " " +
               additionalText().orElse("");
    }

    @Override
    public IModMetadata metadata() {
        return new IModMetadata() {
            @Override
            public String id() {
                return manifest().get("id").getAsString();
            }

            @Override
            public String name() {
                return manifest().get("name").getAsString();
            }

            @Override
            public String version() {
                return manifest().get("version").getAsString();
            }

            @Override
            public String author() {
                return manifest().get("author").getAsString();
            }

            @Override
            public String @NotNull [] mixins() {
                var mixins = manifest().get("mixins");

                if (mixins == null) {
                    return IModMetadata.super.mixins();
                } else if (mixins instanceof JsonArray array) {
                    var out = new String[array.size()];
                    var i = 0;

                    for (var el : array) {
                        out[i++] = el.getAsString();
                    }

                    return out;
                } else {
                    return new String[]{ mixins.getAsString() };
                }
            }
        };
    }
}
