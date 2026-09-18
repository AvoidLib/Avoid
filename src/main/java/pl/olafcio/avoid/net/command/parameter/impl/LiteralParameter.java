package pl.olafcio.avoid.net.command.parameter.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.command.exception.use.CommandSyntaxException;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;

public class LiteralParameter extends CommandParameter<Void> {
    private final String value;

    public LiteralParameter(String name) {
        this(name, name);
    }

    public LiteralParameter(String name, String value) {
        super(name);
        this.value = value;
    }

    @Override
    @NotNull
    public Void parse(String text) throws CommandSyntaxException {
        if (!text.equals(value))
            throw new CommandSyntaxException("Expected '%s'".formatted(value));

        return null;
    }

    @Override
    @Nullable
    public String[] tabcomplete() {
        return new String[]{value};
    }

    public String getValue() {
        return value;
    }
}
