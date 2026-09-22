package pl.olafcio.avoid.mixinclass;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter_kyori.COToNative;
import pl.olafcio.avoid.net.command.executor.UnknownExecutor;

public final class MyUnknownExecutor extends UnknownExecutor {
    private final CommandSourceStack source;

    public MyUnknownExecutor(CommandSourceStack source) {
        this.source = source;
    }

    @Override
    public void sendMessage(BaseComponent<?> component) {
        source.getSender().sendMessage(COToNative.from(component));
    }
}
