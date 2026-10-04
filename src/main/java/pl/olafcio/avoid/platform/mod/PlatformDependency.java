package pl.olafcio.avoid.platform.mod;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.Discouraged;

/**
 * A mod/plugin dependency on the wrapped loader (the actual platform, e.g. Fabric/NeoForge/Paper).
 * @param modId The ID of the mod/plugin to depend on.
 * @param type The relation between the dependant and the dependency.
 * @param loadOrder Defines whether to load the dependant or dependency first.
 */
@ApiStatus.Experimental
@Discouraged(reason = "This might be changed to be an interface or abstract class")
public record PlatformDependency(String modId, PlatformDependency.Type type, LoadOrder loadOrder) {
    /**
     * Defines whether to load the dependant or dependency first.
     */
    public enum LoadOrder {
        BEFORE_MOD,
        AFTER_MOD,
        UNDEFINED
    }

    private sealed interface Type {}

    public enum Hard implements Type {
        /**
         * Indicates the mod is incompatible with this dependency.
         */
        INCOMPATIBLE,

        /**
         * Indicates the mod requires this dependency.
         */
        REQUIRES,

        /**
         * Indicates the mod recommends installing this dependency.
         */
        RECOMMENDS
    }

    public enum Soft implements Type {
        /**
         * Indicates the mod doesn't recommend being used with this dependency.
         */
        INCOMPATIBLE,

        /**
         * Indicates the mod recommends being used with this dependency.
         */
        RECOMMENDS,

        /**
         * Indicates the mod provides functionality and API of another mod.
         */
        PROVIDES
    }
}
