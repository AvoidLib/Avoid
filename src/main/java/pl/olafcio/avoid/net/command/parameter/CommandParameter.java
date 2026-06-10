package pl.olafcio.avoid.net.command.parameter;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.command.exception.use.CommandSyntaxException;

import java.util.HashMap;

@ApiStatus.Internal
@ApiStatus.Experimental
@ApiStatus.NonExtendable
public abstract class CommandParameter<T> {
    private static int index = 0;

    private final int hash;
    private static final HashMap<Class<? extends CommandParameter<?>>, Integer> lookup
                   = new HashMap<>();

    @SuppressWarnings("unchecked")
    public CommandParameter(String name) {
        var klass = getClass();
        if (!lookup.containsKey(klass))
            lookup.put((Class<? extends CommandParameter<?>>)
                       klass, (int) Math.pow(2, ++index));

        this.hash = lookup.get(klass);
        this.name = name;
    }

    public abstract @NotNull T parse(String text) throws CommandSyntaxException;
    public abstract @Nullable String[] tabcomplete();

    public ShouldParse shouldParse() {
        return ShouldParse.TRY;
    }

    private final String name;
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
