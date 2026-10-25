package pl.olafcio.avoid_loader.interfaces;

import org.jetbrains.annotations.NotNull;

public interface IModMetadata {
    String id();
    String name();
    String version();
    String author();

    default String @NotNull [] mixins() {
        return new String[0];
    }
}
