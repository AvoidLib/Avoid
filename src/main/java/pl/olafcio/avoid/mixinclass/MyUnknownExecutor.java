package pl.olafcio.avoid.mixinclass;

import net.minecraft.commands.CommandSourceStack;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.command.executor.UnknownExecutor;

public final class MyUnknownExecutor extends UnknownExecutor {
    private final CommandSourceStack source;

    public MyUnknownExecutor(CommandSourceStack source) {
        this.source = source;
    }

    @Override
    public void sendMessage(BaseComponent<?> component) {
        source.sendSystemMessage(COToNative.from(component));
    }
}
