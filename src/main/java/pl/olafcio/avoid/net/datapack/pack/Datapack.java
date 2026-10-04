package pl.olafcio.avoid.net.datapack.pack;

import pl.olafcio.avoid.annotations.refactor.Discouraged;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.datapack.pack.errors.DatapackAlreadyLoaded;
import pl.olafcio.avoid.net.datapack.pack.errors.DatapackFailedToToggle;

import java.nio.file.Path;
import java.util.Objects;

@WillRefactor(aspect = "package")
@Discouraged(reason = "This may be refactored away, and have the type changed (e.g. to an interface)")
public abstract class Datapack {
    protected final String id;
    protected final BaseComponent<?> name;
    protected final BaseComponent<?> description;
    protected final DatapackCompatibility compatibility;
    protected final Path path;

    public Datapack(
            String id,
            BaseComponent<?> name,
            BaseComponent<?> description,
            DatapackCompatibility compatibility,
            Path location
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.compatibility = compatibility;
        this.path = location;
    }

    /**
     * Loads the datapack. In other words, makes it {@linkplain #isAvailable available}.
     * <br/><br/>
     * Note that if the datapack is {@linkplain #isAvailable already loaded}, this will throw an exception.
     */
    public abstract void load() throws DatapackAlreadyLoaded;

    /**
     * Enables the datapack.<br/><br/>
     * Note that if the datapack is {@linkplain #isEnabled already enabled} or is {@linkplain #isAvailable not available} anymore, this will throw an exception.
     */
    public abstract void enable() throws DatapackFailedToToggle;

    /**
     * Disables the datapack.<br/><br/>
     * Note that if the datapack is {@linkplain #isEnabled not enabled} or is {@linkplain #isAvailable not available} anymore, this will throw an exception.
     */
    public abstract void disable() throws DatapackFailedToToggle;

    /**
     * Returns whether the datapack is still loaded.
     */
    public abstract boolean isAvailable();

    /**
     * Returns whether the datapack is enabled.
     */
    public abstract boolean isEnabled();

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        var that = (Datapack) obj;

        return Objects.equals(this.id, that.id) &&
               Objects.equals(this.name, that.name) &&
               Objects.equals(this.description, that.description) &&
               Objects.equals(this.compatibility, that.compatibility);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, compatibility);
    }

    @Override
    public String toString() {
        return "Pack[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "compatibility=" + compatibility + ']';
    }

    public String id() {
        return id;
    }
    public BaseComponent<?> name() {
        return name;
    }
    public BaseComponent<?> description() {
        return description;
    }
    public DatapackCompatibility compatibility() {
        return compatibility;
    }
    public Path location() {
        return path;
    }
}
