package pl.olafcio.avoid.net.client.server;

import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

import java.util.List;

/**
 * A server entry, from the multiplayer server selection list.
 * @param address The server address. It is commonly referred to as <i>IP</i>, but it actually can also be a domain. And it also can contain a port.
 * @param name The server name in the list. It's chosen by the client.
 * @param motd The server MOTD (Message Of The Day) - in other words, its description.
 * @param ping The player's ping to the server.
 * @param playerList The list of components that show up when you hover over the status text.
 * @param protocol The server's <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol_version_numbers">protocol version</a>.
 * @param status The server's status text. Typically, it is the active player amount - e.g. {@code 15/20}. This can be changed by the server freely, however.
 * @param version A literal containing the name of the server version, set by the client. Typically, it is the running Minecraft version <b>of the client</b>.
 * @param maxPlayers The max amount of players on the server.
 * @param onlinePlayers The amount of online players on the server.
 * @param playerData A list of online players on the server, containing their names and UUIDs.
 * @param iconBytes The (typically PNG) data of the server icon.
 */
@NullMarked
public record ServerEntry(
        String address,
        String name,
        BaseComponent<?> motd,
        long ping,
        List<? extends BaseComponent<?>> playerList,
        int protocol,
        BaseComponent<?> status,
        BaseComponent<?> version,
        @Nullable Integer maxPlayers,
        @Nullable Integer onlinePlayers,
        @Nullable List<PlayerEntry> playerData,
        byte @Nullable[] iconBytes
) {}
