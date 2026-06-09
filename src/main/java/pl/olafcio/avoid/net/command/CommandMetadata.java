package pl.olafcio.avoid.net.command;

import pl.olafcio.avoid.net.command.handling.CommandHandler;

public record CommandMetadata(
        String name,
        SyntaxTree syntaxes,
        CommandHandler unknownhandler
) {}
