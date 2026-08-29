package pl.olafcio.avoid.mods.events_loader;

import pl.olafcio.avoid.mods.AvoidModMeta;

/**
 * Fired when a mod is being enabled.
 */
public record ModEnablingEvent(AvoidModMeta meta) {}
