package pl.olafcio.avoid.net.permission;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

public interface PermissionHolder {
    @Nullable
    default BaseComponent<?> getDisplayName() {
        return null;
    }

    @NotNull
    String getName();
}
