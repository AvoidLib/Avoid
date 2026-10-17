package pl.olafcio.avoid_impl.mixininterface;

import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionSet;

public interface IPermissionSet {
    default boolean avoid$hasPermission(Permission.Atom atom, Permission.HasCommandLevel level) {
        return ((PermissionSet) this).hasPermission(atom) ||
               ((PermissionSet) this).hasPermission(level);
    }
}
