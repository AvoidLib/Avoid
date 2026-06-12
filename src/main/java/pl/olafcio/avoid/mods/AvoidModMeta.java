package pl.olafcio.avoid.mods;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;

@WillRefactor(aspect = "name")
public record AvoidModMeta(
        String id,
        String version,
        String versionSystem,

        String name,
        String author,
        String description,

        Class<? extends AvoidMod> mainClass
) {
    @ApiStatus.Experimental
    public AvoidModMeta {}
}
