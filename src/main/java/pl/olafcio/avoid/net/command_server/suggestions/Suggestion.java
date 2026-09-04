package pl.olafcio.avoid.net.command_server.suggestions;

import com.mojang.datafixers.util.Either;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

public record Suggestion(
        int rangeStart, int rangeEnd,
        String text,
        Either<BaseComponent<?>, String> tooltip
) {}
