package pl.olafcio.avoid.net.entity.custom_internal;

import pl.olafcio.avoid.net.entity.custom.Entity;

@FunctionalInterface
public interface EntityConstructor {
    Entity construct(int id, Object... args);
}
