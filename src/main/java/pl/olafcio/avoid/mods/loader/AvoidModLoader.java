package pl.olafcio.avoid.mods.loader;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.mods.AvoidMod;
import pl.olafcio.avoid.mods.AvoidModMeta;

import java.nio.file.Path;

/**
 * Represents the Avoid addon loader.<br/><br/>
 * <b>NOTE:</b> If you want a Fabric/NeoForge loader API wrapper, take a look at {@link AvoidWrappedLoader}.
 */
@ApiStatus.Experimental
@NullMarked
public final class AvoidModLoader {
    private AvoidModLoader() {}

    /**
     * Checks whether an Avoid addon, under the given ID, is loaded.
     */
    public static boolean isLoadedAddon(String id) {
        return pl.olafcio.avoid_impl.mods.loader.AvoidModLoader.isLoadedAddon(id);
    }

    /**
     * Gets the metadata of an Avoid addon, under the given ID.<br/>
     * If no addon was found (or the addon wasn't loaded yet), {@code null} is returned.
     */
    @Nullable
    public static AvoidModMeta getLoadedAddon(String id) {
        return pl.olafcio.avoid_impl.mods.loader.AvoidModLoader.getLoadedAddon(id);
    }

    /**
     * Gets the main class instance of an Avoid addon, under the given ID.<br/>
     * If no addon was found (or the addon wasn't loaded yet), {@code null} is returned.
     */
    @UnknownNullability
    public static AvoidMod getLoadedAddonClass(AvoidModMeta meta) {
        return pl.olafcio.avoid_impl.mods.loader.AvoidModLoader.getLoadedAddonClass(meta);
    }

    /**
     * Gets the filesystem path of an Avoid addon, under the given ID.<br/>
     * If no addon was found (or the addon wasn't loaded yet), {@code null} is returned.
     */
    @UnknownNullability
    public static Path getLoadedAddonFile(AvoidModMeta meta) {
        return pl.olafcio.avoid_impl.mods.loader.AvoidModLoader.getLoadedAddonFile(meta);
    }

    /**
     * Gets a list containing each loaded mod's metadata.
     */
    @NotNull
    public static AvoidModMeta[] getLoadedAddons() {
        return pl.olafcio.avoid_impl.mods.loader.AvoidModLoader.getLoadedAddons();
    }
}
