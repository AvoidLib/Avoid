package pl.olafcio.avoid_loader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid_loader.abst.ModFile;
import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.services.IModLoader;
import pl.olafcio.avoid_loader.interfaces.standard.IAvoidModMetadata;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Deprecated(forRemoval = true)
public class AvoidModContainer implements IMod {
    private final ModFile modFile;
    private final IModLoader origin;
    private final IAvoidModMetadata manifest;
    private final AvoidModContainer parent;
    private final ArrayList<IMod> children;
    private       String additionalText;

    public AvoidModContainer(ModFile modFile, IModLoader origin, IAvoidModMetadata manifest, AvoidModContainer parent, ArrayList<IMod> children) {
        this.modFile = modFile;
        this.origin = origin;
        this.manifest = manifest;
        this.parent = parent;
        this.children = children;
        this.additionalText = null;
    }

    public AvoidModContainer(ModFile modFile, IModLoader origin, IAvoidModMetadata manifest, AvoidModContainer parent) {
        this.modFile = modFile;
        this.origin = origin;
        this.manifest = manifest;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.additionalText = null;
    }

    public AvoidModContainer(ModFile modFile, IModLoader origin, IAvoidModMetadata manifest, AvoidModContainer parent, ArrayList<IMod> children, String additionalText) {
        this.modFile = modFile;
        this.origin = origin;
        this.manifest = manifest;
        this.parent = parent;
        this.children = children;
        this.additionalText = additionalText;
    }

    public AvoidModContainer(ModFile modFile, IModLoader origin, IAvoidModMetadata manifest, AvoidModContainer parent, String additionalText) {
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
    @Nullable
    public Map<String, ?> manifest() {
        return manifest.map();
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
        return metadata().name() + " " +
               metadata().version() + " " +
               additionalText().orElse("");
    }

    @Override
    @NotNull
    public IAvoidModMetadata metadata() {
        return manifest;
    }
}
