package pl.olafcio.avoid.net.entity_renderer;

import pl.olafcio.avoid.net.entity.Entity;

public abstract class EntityRenderer<T extends Entity, S> {
    public abstract void render(T entity, S state, float tickDelta);
}
