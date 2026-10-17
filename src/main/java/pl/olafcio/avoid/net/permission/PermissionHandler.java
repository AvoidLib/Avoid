package pl.olafcio.avoid.net.permission;

import pl.olafcio.avoid.net.command.annotation.PermissionLevel;

public interface PermissionHandler {
    PermissionStatus hasPermission(PermissionHolder executor, String node);
    PermissionStatus hasPermissionLevel(PermissionHolder executor, PermissionLevel.Enum permissionLevel);
}
