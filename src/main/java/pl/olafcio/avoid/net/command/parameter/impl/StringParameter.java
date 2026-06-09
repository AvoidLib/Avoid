package pl.olafcio.avoid.net.command.parameter.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;

public class StringParameter extends CommandParameter<String> {
    public StringParameter(String name) {
        super(name);
    }

    @Override
    @NotNull
    public String parse(String text) {
        return text;
    }

    @Override
    @Nullable
    public String[] tabcomplete() {
        return null;
    }
}
