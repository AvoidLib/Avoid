package pl.olafcio.avoid.net.command.executor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.permission.PermissionHolder;

public abstract class UnknownExecutor implements PermissionHolder, Executor {
    @Override
    public @Nullable BaseComponent<?> getDisplayName() {
        return PermissionHolder.super.getDisplayName();
    }

    @Override
    @NotNull
    public String getName() {
        return "unknown";
    }

    @Override
    public abstract void sendMessage(BaseComponent<?> component);
}
