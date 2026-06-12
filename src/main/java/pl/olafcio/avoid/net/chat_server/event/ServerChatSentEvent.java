package pl.olafcio.avoid.net.chat_server.event;

import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.player.Player;

/**
 * Fired when the server sent a system message to a player.<br/>
 * This event will also be fired for player-sent messages.
 */
public record ServerChatSentEvent(@NotNull Player player, @NotNull BaseComponent<?> message) {}
