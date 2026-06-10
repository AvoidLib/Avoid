package pl.olafcio.avoid.net.entity;

import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.world.Vect3;

import java.util.UUID;

public class Entity {
    private final int id;
    private final EntityType type;
    private final Vect3 velocity;
    private final UUID uuid;
    private final String uuidString;
    private final BaseComponent<?> name;

    public Entity(int id, EntityType type, Vect3 velocity, UUID uuid, String uuidString, BaseComponent<?> name) {
        this.id = id;
        this.type = type;
        this.velocity = velocity;
        this.uuid = uuid;
        this.uuidString = uuidString;
        this.name = name;
    }

    public int id() {
        return id;
    }

    public EntityType type() {
        return type;
    }

    public Vect3 velocity() {
        return velocity;
    }

    public UUID uuid() {
        return uuid;
    }

    public String uuidString() {
        return uuidString;
    }

    public BaseComponent<?> getDisplayName() {
        return name;
    }

    @Override
    public String toString() {
        return "Entity[" +
                "id=" + id + ", " +
                "type=" + type + ", " +
                "velocity=" + velocity + ", " +
                "uuid=" + uuid + ", " +
                "uuidString=" + uuidString + ", " +
                "name=" + uuid + ']';
    }
}
