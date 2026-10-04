package pl.olafcio.avoid.platform.mod;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.Discouraged;
import pl.olafcio.avoid.util.ModEnvironment;

import java.util.List;
import java.util.Set;

/**
 * A mod/plugin on the wrapped loader (the actual platform, e.g. Fabric/NeoForge/Paper).
 * @param id The mod ID.
 * @param name The mod display name.
 * @param description The mod description.
 * @param authors The mod authors. May be empty.
 * @param contributors The mod contributors. May be empty.
 * @param contact The mod contact information.
 * @param licenses The licenses used in the mod.
 * @param version The mod version.
 * @param environment The environments the mod should run on.
 * @param dependencies The mod's dependencies.
 * @param type The mod type.
 */
@ApiStatus.Experimental
@Discouraged(reason = "This might be changed to be an interface or abstract class")
public record PlatformMod(
        String id,
        String name,
        String description,
        PlatformContributor[] authors,
        PlatformContributor[] contributors,
        PlatformContact contact,
        Set<String> licenses,
        String version,
        ModEnvironment environment,
        List<PlatformDependency> dependencies,
        PlatformMod.Type type
) {
    /**
     * A mod/plugin type.
     */
    public enum Type {
        /**
         * A built-in mod, e.g. {@code minecraft}.
         */
        BUILTIN,

        /**
         * A fabric mod, e.g. Fabric API.
         */
        FABRIC,

        /**
         * A sponge mod, e.g. <a href="https://modrinth.com/plugin/landiscovery">LanDiscovery</a>.
         */
        SPONGE,

        /**
         * A neoforge mod, e.g. <a href="https://modrinth.com/mod/the-broken-script">The Broken Script</a>.
         */
        NEOFORGE,

        /**
         * A paper plugin, e.g. <a href="https://modrinth.com/plugin/worldguard">WorldGuard</a>.
         */
        PAPER,

        /**
         * A non-standard mod/plugin, e.g. one added by a fork of Fabric.
         */
        CUSTOM
    }
}
