package pl.olafcio.avoid_impl.net.permission;

import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.Permission;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.permission.PermissionHandler;
import pl.olafcio.avoid_impl.mixinclass.UnassignedAtom;

import java.util.ArrayList;

public final class PermissionHandlers {
    @ApiStatus.Internal
    private PermissionHandlers() {}

    public static final ArrayList<PermissionHandler> PERMISSION_HANDLERS
                  = new ArrayList<>();

    public static void register(PermissionHandler permissionHandler) {
        PERMISSION_HANDLERS.add(permissionHandler);
    }

    @NotNull
    public static Permission.Atom createAtom(String cast) {
        if (cast.indexOf(':') == -1)
            return new UnassignedAtom(Identifier.withDefaultNamespace(cast));

        return Permission.Atom.create(cast);
    }
}
