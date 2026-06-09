package pl.olafcio.avoid.net.command;

import pl.olafcio.avoid.net.command.handling.CommandHandler;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;

import java.util.LinkedHashMap;

public final class SyntaxTree extends LinkedHashMap<CommandParameter<?>, SyntaxTree> {
    public CommandHandler method;

    public SyntaxTree() {
        super();
        this.method = null;
    }

    public SyntaxTree(CommandHandler method) {
        super();
        this.method = method;
    }

    public boolean isNodeExecutable() {
        return method != null;
    }
}
