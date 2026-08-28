package pl.olafcio.avoid.mods.events_loader;

import pl.olafcio.avoid.mods.AvoidModMeta;

/**
 * Fired when a mod has been loaded.
 */
public record ModLoadedEvent(AvoidModMeta meta) {}
