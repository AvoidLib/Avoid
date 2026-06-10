package pl.olafcio.avoid.net.entity;

import net.minecraft.world.phys.Vec3;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.entity_type.EntityType;

import java.util.UUID;

public class Entity {
    private final int id;
    private final EntityType type;
    private final Vec3 velocity;
    private final UUID uuid;
    private final String uuidString;
    private final BaseComponent<?> name;

    public Entity(int id, EntityType type, Vec3 velocity, UUID uuid, String uuidString, BaseComponent<?> name) {
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

    public Vec3 velocity() {
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
