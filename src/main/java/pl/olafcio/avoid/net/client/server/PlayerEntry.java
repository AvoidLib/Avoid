package pl.olafcio.avoid.net.client.server;

import java.util.UUID;

/**
 * A player entry, from the {@linkplain ServerEntry#playerData() player data} on the {@link ServerEntry}.
 * @param uuid The player's UUID <i>(Universally Unique Identifier)</i>.
 * @param name The player's nickname.
 */
public record PlayerEntry(UUID uuid, String name) {}
