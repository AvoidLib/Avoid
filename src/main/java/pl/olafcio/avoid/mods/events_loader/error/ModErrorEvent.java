package pl.olafcio.avoid.mods.events_loader.error;

import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid_loader.interfaces.IMod;

import java.nio.file.Path;
import java.util.Objects;

/**
 * Fired when an error has occurred while loading an Avoid mod.
 */
public final class ModErrorEvent {
    private final IMod mod;
    private final Path path;
    private final Exception error;

    public ModErrorEvent(IMod mod, Exception error) {
        this.mod = mod;
        this.path = mod.getModFile().getPath();
        this.error = error;
    }

    public ModErrorEvent(Path mod, Exception error) {
        this.mod = null;
        this.path = mod;
        this.error = error;
    }

    public Path mod() {
        return path;
    }

    @Nullable
    public IMod modInstance() {
        return mod;
    }

    public Exception error() {
        return error;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ModErrorEvent) obj;
        return Objects.equals(this.mod, that.mod) &&
               Objects.equals(this.path, that.path) &&
               Objects.equals(this.error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mod, error);
    }

    @Override
    public String toString() {
        return "ModErrorEvent[" +
                "mod=" + path + ", " +
                "error=" + error + ']';
    }
}
