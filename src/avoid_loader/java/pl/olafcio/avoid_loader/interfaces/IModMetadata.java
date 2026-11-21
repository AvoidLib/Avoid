package pl.olafcio.avoid_loader.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface IModMetadata {
    String id();
    String name();
    String version();
    String author();

    default String @NotNull [] mixins() {
        return new String[0];
    }

    default Map<String, Object> map() {
        return Map.of();
    }
}
