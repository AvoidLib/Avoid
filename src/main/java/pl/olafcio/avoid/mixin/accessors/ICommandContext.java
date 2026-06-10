package pl.olafcio.avoid.mixin.accessors;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(CommandContext.class)
public interface ICommandContext<S> {
    @Accessor("arguments")
    Map<String, ParsedArgument<S, ?>> avoid$arguments();

    @Accessor("arguments")
    @Final
    @Mutable
    void avoid$arguments(Map<String, ParsedArgument<S, ?>> value);
}
