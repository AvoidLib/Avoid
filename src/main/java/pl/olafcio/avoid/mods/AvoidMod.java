package pl.olafcio.avoid.mods;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

/**
 * An abstract class for all Avoid addons.
 */
@NeverRemoval
public abstract class AvoidMod {
    /**
     * Invoked when the modloader has scanned all the mod's classes and registered it fully.
     */
    @ApiStatus.Experimental
    public void onLoad() {}

    /**
     * Invoked when all addons have been loaded.
     */
    @ApiStatus.Experimental
    public void onEnable() {}

    /**
     * Invoked when:
     * - if the mod is running inside a client: the client stops;
     * - if the mod is running inside a server: the server stops.
     */
    @ApiStatus.Experimental
    public void onDisable() {}

    /**
     * Invoked when the client stops.
     */
    @ApiStatus.Experimental
    public void onClientDisable() {}

    /**
     * Invoked when the physical or integrated server stops.
     */
    @ApiStatus.Experimental
    public void onServerDisable() {}
}
