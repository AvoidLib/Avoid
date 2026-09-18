package pl.olafcio.avoid.net.entity.type;

import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.values.EnderDragonPhase;
import pl.olafcio.avoid.net.entity.values.EnderDragonPhaseNative;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.world.vect3.IVect3;

import java.util.UUID;

public abstract class EnderDragon extends Entity {
    public EnderDragon(int id, EntityType type, IVect3 position, IVect3 velocity, UUID uuid, String uuidString, BaseComponent<?> name, net.minecraft.world.entity.Entity underlyingEntity) {
        super(id, type, position, velocity, uuid, uuidString, name, underlyingEntity);
    }

    public EnderDragonPhase getPhase() {
        return EnderDragonPhaseNative.convert(__cast(net.minecraft.world.entity.boss.enderdragon.EnderDragon.class).getPhaseManager().getCurrentPhase().getPhase());
    }

    /**
     * <b>NOTE:</b> This may break the ender dragon.
     */
    public void setPhase(EnderDragonPhase value) {
        __cast(net.minecraft.world.entity.boss.enderdragon.EnderDragon.class).getPhaseManager().setPhase(EnderDragonPhaseNative.convertFrom(value));
    }
}
