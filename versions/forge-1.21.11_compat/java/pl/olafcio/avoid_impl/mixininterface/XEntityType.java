package pl.olafcio.avoid_impl.mixininterface;

import pl.olafcio.avoid_impl.net.entity_type.AvoidEntityType;

public interface XEntityType {
    AvoidEntityType avoid$entityType();
    void avoid$entityType(AvoidEntityType value);
}
