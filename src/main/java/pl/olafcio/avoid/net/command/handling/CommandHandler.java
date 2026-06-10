package pl.olafcio.avoid.net.command.handling;

import pl.olafcio.avoid.net.command.annotation.Syntax;
import pl.olafcio.avoid.net.command.annotation.Unknown;

/**
 * A method handling command execution, typically annotated with <a style="color: #3887a1">@{@linkplain Syntax}</a>
 * or <a style="color: #3887a1">@{@linkplain Unknown}</a>.
 */
@FunctionalInterface
public interface CommandHandler {
    void run(Usage usage);
}
