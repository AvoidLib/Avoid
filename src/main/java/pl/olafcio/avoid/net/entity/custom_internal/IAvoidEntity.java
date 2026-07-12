package pl.olafcio.avoid.net.entity.custom_internal;

import pl.olafcio.avoid.net.entity.custom.Entity;

public interface IAvoidEntity {
    Entity getAvoidEntity();
    void parentTick();
}
