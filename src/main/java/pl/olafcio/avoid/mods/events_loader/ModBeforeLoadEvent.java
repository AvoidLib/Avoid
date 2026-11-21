package pl.olafcio.avoid.mods.events_loader;

import com.google.gson.JsonObject;
import pl.olafcio.avoid_loader.interfaces.standard.IAvoidModMetadata;

import java.util.Objects;
import java.util.jar.JarFile;

/**
 * Fired just before a mod's main class should be loaded.
 */
public final class ModBeforeLoadEvent {
    private final String id;
    private final IAvoidModMetadata meta;
    @Deprecated(forRemoval = true)
    private final JsonObject manifest;
    private final JarFile jar;

    public ModBeforeLoadEvent(String id, IAvoidModMetadata meta, @Deprecated(forRemoval = true) JsonObject manifest, JarFile jar) {
        this.id = id;
        this.meta = meta;
        this.manifest = manifest;
        this.jar = jar;
    }

    public String id() {
        return id;
    }

    public IAvoidModMetadata metadata() {
        return meta;
    }

    @Deprecated(forRemoval = true)
    public JsonObject manifest() {
        return manifest;
    }

    public JarFile jar() {
        return jar;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ModBeforeLoadEvent) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.manifest, that.manifest) &&
                Objects.equals(this.jar, that.jar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, manifest, jar);
    }

    @Override
    public String toString() {
        return "ModBeforeLoadEvent[" +
                "id=" + id + ", " +
                "manifest=" + manifest + ", " +
                "jar=" + jar + ']';
    }
}
