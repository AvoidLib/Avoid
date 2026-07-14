package pl.olafcio.avoid;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.mods.AvoidMod;
import pl.olafcio.avoid.mods.AvoidModMeta;
import pl.olafcio.avoid.mods.loader.AvoidModLoader;

import java.nio.file.Path;

/**
 * @deprecated Moved to {@link AvoidModLoader}.
 */
@Deprecated(forRemoval = true, since = "v1.10")
@NullMarked
public final class AvoidManager {
    private AvoidManager() {}

    /**
     * @deprecated Use {@link AvoidModLoader#isLoadedAddon} instead.
     */
    @Deprecated(forRemoval = true, since = "v1.10")
    public static boolean isLoadedAddon(String id) {
        return AvoidModLoader.isLoadedAddon(id);
    }

    /**
     * @deprecated Use {@link AvoidModLoader#getLoadedAddon} instead.
     */
    @Nullable
    public static AvoidModMeta getLoadedAddon(String id) {
        return AvoidModLoader.getLoadedAddon(id);
    }

    /**
     * @deprecated Use {@link AvoidModLoader#getLoadedAddonClass} instead.
     */
    @UnknownNullability
    public static AvoidMod getLoadedAddonClass(AvoidModMeta meta) {
        return AvoidModLoader.getLoadedAddonClass(meta);
    }

    /**
     * @deprecated Use {@link AvoidModLoader#getLoadedAddonFile} instead.
     */
    @UnknownNullability
    public static Path getLoadedAddonFile(AvoidModMeta meta) {
        return AvoidModLoader.getLoadedAddonFile(meta);
    }

    /**
     * @deprecated Use {@link AvoidModLoader#getLoadedAddons} instead.
     */
    public static AvoidModMeta[] getLoadedAddons() {
        return AvoidModLoader.getLoadedAddons();
    }
}
