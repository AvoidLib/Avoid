package pl.olafcio.avoid.net.datapack.pack;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.*;
import net.minecraft.server.packs.repository.Pack.ResourcesSupplier;
import net.minecraft.server.packs.repository.PackSource;
import pl.olafcio.avoid.annotations.refactor.Discouraged;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.datapack.pack.errors.DatapackAlreadyLoaded;
import pl.olafcio.avoid.net.datapack.pack.errors.DatapackFailedToToggle;
import pl.olafcio.avoid_impl.AvoidInternal;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

@WillRefactor(aspect = "package")
@Discouraged(reason = "This may be refactored away, and have the type changed (e.g. to an interface)")
public final class Datapack {
    private final String id;
    private final BaseComponent<?> name;
    private final BaseComponent<?> description;
    private final DatapackCompatibility compatibility;
    private final Path path;

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
    public void load() throws DatapackAlreadyLoaded {
        if (isAvailable())
            throw new DatapackAlreadyLoaded("[Datapack#load] Datapack '%s' is already loaded".formatted(id));

        var source = PackSource.create(x -> x, false);

        var name = path.getFileName().toString();
        var info = new PackLocationInfo("avoid/" + name, Component.literal(name), source, Optional.empty());

        ResourcesSupplier resourcesSupplier;

        if (Files.isDirectory(path))
            resourcesSupplier = new PathPackResources.PathResourcesSupplier(path);
        else
            resourcesSupplier = new FilePackResources.FileResourcesSupplier(path);

        var config = new PackSelectionConfig(false, net.minecraft.server.packs.repository.Pack.Position.TOP, false);
        var meta = net.minecraft.server.packs.repository.Pack.readMetaAndCreate(info, resourcesSupplier, PackType.SERVER_DATA, config);

        AvoidInternal.getServer().getPackRepository().available.put(id, meta);
    }

    /**
     * Enables the datapack.<br/><br/>
     * Note that if the datapack is {@linkplain #isEnabled already enabled} or is {@linkplain #isAvailable not available} anymore, this will throw an exception.
     */
    public void enable() throws DatapackFailedToToggle {
        if (!AvoidInternal.getServer().getPackRepository().addPack(id))
            throw new DatapackFailedToToggle("[Datapack#enable] Datapack '%s' could not be enabled");
    }

    /**
     * Disables the datapack.<br/><br/>
     * Note that if the datapack is {@linkplain #isEnabled not enabled} or is {@linkplain #isAvailable not available} anymore, this will throw an exception.
     */
    public void disable() throws DatapackFailedToToggle {
        if (!AvoidInternal.getServer().getPackRepository().removePack(id))
            throw new DatapackFailedToToggle("[Datapack#disable] Datapack '%s' could not be disabled");
    }

    /**
     * Returns whether the datapack is still loaded.
     */
    public boolean isAvailable() {
        return AvoidInternal.getServer().getPackRepository().isAvailable(id);
    }

    /**
     * Returns whether the datapack is enabled.
     */
    public boolean isEnabled() {
        return AvoidInternal.getServer().getPackRepository().getSelectedIds().contains(id);
    }

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
