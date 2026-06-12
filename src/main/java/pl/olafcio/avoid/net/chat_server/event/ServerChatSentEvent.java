package pl.olafcio.avoid.net.chat_server.event;

import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.player.Player;

public record ServerChatSentEvent(@NotNull Player player, @NotNull BaseComponent<?> message) {}
