package pl.olafcio.avoid_impl.mixininterface;

import pl.olafcio.avoid.net.permission.PermissionHolder;

public interface INameAndId {
    PermissionHolder avoid$owner();
    void avoid$owner(PermissionHolder value);
}
