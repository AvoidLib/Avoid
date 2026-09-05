package pl.olafcio.avoid.net.player;

import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.world.vect3.IVect3;

public record Raycast(Type type, IVect3 position, @Nullable Entity entity) {
    /**
     * The type of the object the raycast is directed at.
     */
    public enum Type {
        MISS,
        BLOCK,
        ENTITY
    }

    @Nullable
    public Entity getEntity() {
        return entity;
    }
}
