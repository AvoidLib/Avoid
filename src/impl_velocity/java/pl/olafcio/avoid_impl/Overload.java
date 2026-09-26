package pl.olafcio.avoid_impl;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;

import java.util.LinkedHashMap;
import java.util.function.BiFunction;

final class Overload {
    public BiFunction<CommandContext<CommandSource>, Boolean, Integer> load;
    public Command<CommandSource> execute;
    public LinkedHashMap<String, CommandParameter<?>> mappings;
}
