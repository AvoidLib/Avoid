package pl.olafcio.avoid.net.command.executor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.command.annotation.PermissionLevel;
import pl.olafcio.avoid.net.permission.PermissionHolder;

public interface Executor extends PermissionHolder {
    @Nullable
    default BaseComponent<?> getDisplayName() {
        return null;
    }

    @NotNull
    String getName();

    void sendMessage(BaseComponent<?> component);

    @Override
    default boolean hasPermission(String text) {
        return false;
    }

    @Override
    default boolean hasPermission(String text, PermissionLevel.Enum level) {
        return false;
    }
}
