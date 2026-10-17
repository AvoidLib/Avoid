package pl.olafcio.avoid.net.permission;

import pl.olafcio.avoid.net.command.annotation.PermissionLevel;
import pl.olafcio.avoid.net.command.executor.Executor;

public interface PermissionHandler {
    PermissionStatus hasPermission(Executor executor, String node);
    PermissionStatus hasPermissionLevel(Executor executor, PermissionLevel.Enum permissionLevel);
}
