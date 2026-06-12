package pl.olafcio.avoid.net.command.executor;

import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

public abstract class UnknownExecutor implements Executor {
    @Override
    @NotNull
    public String getName() {
        return "unknown";
    }

    @Override
    public abstract void sendMessage(BaseComponent<?> component);
}
