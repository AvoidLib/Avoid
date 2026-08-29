package pl.olafcio.avoid.mods.events_loader.scanning;

import pl.olafcio.avoid.mods.AvoidModMeta;

/**
 * Fired when the mod class analysis phase has been reached.<br/>
 * This is the second, and last, mod class analysis phase.
 */
public record ModClassAnalyzingEvent(AvoidModMeta meta) {}
