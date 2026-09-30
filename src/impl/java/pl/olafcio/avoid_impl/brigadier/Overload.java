package pl.olafcio.avoid_impl.brigadier;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;

import java.util.LinkedHashMap;
import java.util.function.BiFunction;

public final class Overload<Src> {
    public BiFunction<CommandContext<Src>, Boolean, Integer> load;
    public Command<Src> execute;
    public LinkedHashMap<String, CommandParameter<?>> mappings;
}
