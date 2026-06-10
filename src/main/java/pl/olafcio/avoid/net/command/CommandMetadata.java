package pl.olafcio.avoid.net.command;

import pl.olafcio.avoid.net.command.annotation.Unknown;
import pl.olafcio.avoid.net.command.handling.CommandHandler;

/**
 * The metadata of a command.
 * @param name The name of the command.
 * @param syntaxes The syntax tree for the command. See: {@link SyntaxTree}.
 * @param unknownhandler The method to handle non-registered syntaxes. Only set if any method was annotated with <a style="color: #3887a1">@{@linkplain Unknown}</a> (which is optional).
 */
public record CommandMetadata(
        String name,
        SyntaxTree syntaxes,
        CommandHandler unknownhandler
) {}
