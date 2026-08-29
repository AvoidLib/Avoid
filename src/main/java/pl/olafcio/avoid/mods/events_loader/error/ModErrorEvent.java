package pl.olafcio.avoid.mods.events_loader.error;

import java.nio.file.Path;

/**
 * Fired when an error has occurred while loading an Avoid mod.
 */
public record ModErrorEvent(Path mod, Exception error) {}
