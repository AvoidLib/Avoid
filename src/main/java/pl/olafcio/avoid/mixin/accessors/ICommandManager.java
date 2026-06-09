package pl.olafcio.avoid.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import pl.olafcio.avoid.net.command.Command;
import pl.olafcio.avoid.net.command.CommandManager;

import java.util.function.Consumer;

@Mixin(value = CommandManager.class, remap = false)
public interface ICommandManager {
    @Invoker("each")
    static void avoid$each(Consumer<Command> callback) {}
}
