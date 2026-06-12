package pl.olafcio.avoid.net.command.parameter;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.chat.component.Colors;
import pl.olafcio.avoid.net.chat.component.Components;
import pl.olafcio.avoid.net.command.exception.use.CommandSyntaxException;
import pl.olafcio.avoid.net.command.executor.Executor;

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

    public boolean sendSyntaxException(
            @NotNull Executor executor,
            @NotNull CommandContext<CommandSourceStack> ctx
    ) {
        return false;
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
