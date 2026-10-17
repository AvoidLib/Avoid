package pl.olafcio.avoid_impl.net.permission;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.permission.PermissionHandler;

import java.util.ArrayList;

public final class PermissionHandlers {
    @ApiStatus.Internal
    private PermissionHandlers() {}

    public static final ArrayList<PermissionHandler> PERMISSION_HANDLERS
                  = new ArrayList<>();

    public static void register(PermissionHandler permissionHandler) {
        PERMISSION_HANDLERS.add(permissionHandler);
    }
}
