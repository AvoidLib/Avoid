package pl.olafcio.avoid.mods.events_loader.scanning;

import pl.olafcio.avoid.mods.AvoidModMeta;

/**
 * Fired when the mod class collection phase has been reached.<br/>
 * This is the first class analysis phase.
 */
public record ModClassCollectingEvent(AvoidModMeta meta) {}
