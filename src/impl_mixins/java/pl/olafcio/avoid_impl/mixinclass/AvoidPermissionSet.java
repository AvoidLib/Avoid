package pl.olafcio.avoid_impl.mixinclass;

import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.server.permissions.PermissionLevel;
import pl.olafcio.avoid.net.permission.PermissionHolder;

public class AvoidPermissionSet extends AvoidBasePermissionSet<LevelBasedPermissionSet>
                                implements LevelBasedPermissionSet
{
    public AvoidPermissionSet(LevelBasedPermissionSet delegate, PermissionHolder holder) {
        super(delegate, holder);
    }

    @Override
    public PermissionLevel level() {
        return delegate.level();
    }
}
