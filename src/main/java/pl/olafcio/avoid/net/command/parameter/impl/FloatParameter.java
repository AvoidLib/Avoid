package pl.olafcio.avoid.net.command.parameter.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.command.exception.use.CommandSyntaxException;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;

public class FloatParameter extends CommandParameter<Float> {
    public FloatParameter(String name) {
        super(name);
    }

    @Override
    @NotNull
    public Float parse(String text) throws CommandSyntaxException {
        try {
            return Float.parseFloat(text);
        } catch (NumberFormatException e) {
            throw new CommandSyntaxException(e);
        }
    }

    @Override
    @Nullable
    public String[] tabcomplete() {
        return null;
    }
}
