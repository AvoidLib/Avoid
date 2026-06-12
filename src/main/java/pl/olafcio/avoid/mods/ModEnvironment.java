package pl.olafcio.avoid.mods;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public enum ModEnvironment {
    /**
     * Means the mod should load everywhere.
     */
    ALL,

    /**
     * Means the mod should only load on the client.
     */
    CLIENT,

    /**
     * Means the mod should only load on the dedicated server.
     */
    SERVER
}
