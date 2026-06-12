package pl.olafcio.avoid.net.chat.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.tag.ChatTag;

public record ClientChatReceivedEvent(@NotNull BaseComponent<?> message, @Nullable ChatTag tag) {}
