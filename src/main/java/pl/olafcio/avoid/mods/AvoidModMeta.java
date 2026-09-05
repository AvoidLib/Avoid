package pl.olafcio.avoid.mods;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;

import java.util.List;

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
