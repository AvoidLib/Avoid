package pl.olafcio.avoid.net.entity_server.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.entity.Entity;

@ApiStatus.Experimental
public final class ServerEntitySetHealthEvent extends Cancellable {
    private final Entity entity;

    private float value;
    private final float defaultValue;
    private final float prevValue;

    public ServerEntitySetHealthEvent(Entity entity, float value, float prevValue) {
        this.entity = entity;
        this.value =
        this.defaultValue = value;
        this.prevValue = prevValue;
    }

    public Entity getEntity() {
        return entity;
    }

    public float getLevel() {
        return value;
    }

    public void setLevel(float value) {
        this.value = value;
    }

    public float getDefaultLevel() {
        return defaultValue;
    }

    public float getPreviousLevel() {
        return prevValue;
    }

    public boolean isLevelChanged() {
        return value != defaultValue;
    }
}
