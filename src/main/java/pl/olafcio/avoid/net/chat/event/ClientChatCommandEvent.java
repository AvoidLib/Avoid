package pl.olafcio.avoid.net.chat.event;

import org.jetbrains.annotations.NotNull;

public record ClientChatCommandEvent(@NotNull String message) {}
