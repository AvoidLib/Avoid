package pl.olafcio.avoid.net.command.executor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

public interface Executor {
    @Nullable
    default BaseComponent<?> getDisplayName() {
        return null;
    }

    @NotNull
    String getName();

    void sendMessage(BaseComponent<?> component);
}
