package pl.olafcio.avoid.net.entity.type;

import net.minecraft.world.entity.item.PrimedTnt;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.world.vect3.IVect3;

import java.util.UUID;

public abstract class PrimedTNT extends Entity {
    public PrimedTNT(int id, EntityType type, IVect3 position, IVect3 velocity, UUID uuid, String uuidString, BaseComponent<?> name, net.minecraft.world.entity.Entity underlyingEntity) {
        super(id, type, position, velocity, uuid, uuidString, name, underlyingEntity);
    }

    public int getFuse() {
        return __cast(PrimedTnt.class).getFuse();
    }

    /**
     * <b>NOTE:</b> This may break the ender dragon.
     */
    public void setFuse(int value) {
        __cast(PrimedTnt.class).setFuse(value);
    }
}
