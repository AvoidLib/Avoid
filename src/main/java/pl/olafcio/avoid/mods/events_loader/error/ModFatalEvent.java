package pl.olafcio.avoid.mods.events_loader.error;

import java.nio.file.Path;

/**
 * Fired when a fatal error has occurred while loading an Avoid mod,<br/>
 * which will cause the game to crash after the event.
 */
public record ModFatalEvent(Path mod, Exception error) {}
