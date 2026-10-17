package pl.olafcio.avoid.net.permission;

import org.jetbrains.annotations.ApiStatus;

public final class PermissionHandlers {
    @ApiStatus.Internal
    private PermissionHandlers() {}

    public static void register(PermissionHandler permissionHandler) {
        pl.olafcio.avoid_impl.net.permission.PermissionHandlers.register(permissionHandler);
    }
}
