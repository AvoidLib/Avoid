package pl.olafcio.avoid.net.entity.custom_internal;

import pl.olafcio.avoid.net.entity.Entity;

@FunctionalInterface
public interface EntityConstructor<T extends Entity> {
    T construct(int id, Object... args);
}
