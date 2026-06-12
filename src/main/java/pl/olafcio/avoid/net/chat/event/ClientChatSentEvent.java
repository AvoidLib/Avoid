package pl.olafcio.avoid.net.chat.event;

import org.jetbrains.annotations.NotNull;

public record ClientChatSentEvent(@NotNull String message) {}
