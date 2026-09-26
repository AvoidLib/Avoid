package pl.olafcio.avoid_impl;

import com.velocitypowered.api.command.CommandSource;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid_impl.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.command.executor.UnknownExecutor;

final class MyUnknownExecutor extends UnknownExecutor {
    private final CommandSource source;

    public MyUnknownExecutor(CommandSource source) {
        this.source = source;
    }

    @Override
    public void sendMessage(BaseComponent<?> component) {
        source.sendMessage(COToNative.from(component));
    }
}
