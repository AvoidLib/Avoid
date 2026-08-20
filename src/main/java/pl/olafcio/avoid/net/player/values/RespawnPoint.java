package pl.olafcio.avoid.net.player.values;

import pl.olafcio.avoid.net.world.location.Location;

/**
 * A player's respawn point.
 * @param location The location where the player respawns.
 * @param force If {@code true}, safety checking of the location is skipped.
 */
public record RespawnPoint(Location location, boolean force) {}
