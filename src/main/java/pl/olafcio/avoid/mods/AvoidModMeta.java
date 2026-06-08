package pl.olafcio.avoid.mods;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public record AvoidModMeta(
        String id,
        String version,
        String versionSystem,

        String name,
        String author,
        String description,

        Class<? extends AvoidMod> mainClass
) {}
