package pl.olafcio.avoid.mods;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.util.ModEnvironment;

import java.util.List;

/**
 * The metadata of an Avoid mod.
 * @param id The mod's ID.
 * @param version The version of the mod.
 * @param versionSystem The version system used by the mod (e.g. {@code semver}).
 * @param name The name of the mod.
 * @param author The mod's author.
 * @param description The mod's description.
 * @param environment Environments the mod runs on.
 * @param libraries Libraries from the maven central that the mod requires.
 * @param mainClass Main class of the mod.
 */
@WillRefactor(aspect = "name")
public record AvoidModMeta(
        String id,
        String version,
        String versionSystem,

        String name,
        String author,
        String description,

        ModEnvironment environment,
        List<String> libraries,

        Class<? extends AvoidMod> mainClass
) {
    @ApiStatus.Experimental
    public AvoidModMeta(
            String id,
            String version,
            String versionSystem,

            String name,
            String author,
            String description,

            ModEnvironment environment,

            Class<? extends AvoidMod> mainClass
    ) {
        this(id, version, versionSystem, name, author, description, environment, List.of(), mainClass);
    }
}
