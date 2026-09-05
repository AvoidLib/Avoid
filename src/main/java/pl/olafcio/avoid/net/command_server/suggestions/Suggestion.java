package pl.olafcio.avoid.net.command_server.suggestions;

import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid_common.Either;

public record Suggestion(
        int rangeStart, int rangeEnd,
        String text,
        Either<BaseComponent<?>, String> tooltip
) {}
