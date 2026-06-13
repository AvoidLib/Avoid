package pl.olafcio.avoid.mixinclass;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;

import java.util.LinkedHashMap;
import java.util.function.BiFunction;

public final class Overload {
    public BiFunction<CommandContext<CommandSourceStack>, Boolean, Integer> load;
    public Command<CommandSourceStack> execute;
    public LinkedHashMap<String, CommandParameter<?>> mappings;
}
