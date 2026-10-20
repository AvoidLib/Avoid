package pl.olafcio.avoid.net.permission;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.command.annotation.PermissionLevel;

public interface PermissionHolder {
    @Nullable
    default BaseComponent<?> getDisplayName() {
        return null;
    }

    @NotNull
    String getName();

    boolean hasPermission(String text);
    boolean hasPermission(String text, PermissionLevel.Enum level);
}
