package pl.olafcio.avoid_impl.mixinclass;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import pl.olafcio.avoid_impl.mixininterface.IPermissionSet;

public interface PermissionSetImpl
       extends LevelBasedPermissionSet, IPermissionSet {}
