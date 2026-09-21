package pl.olafcio.avoid.net.entity.type;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.type.base.annotations.AutoEntityAttach;
import pl.olafcio.avoid.net.entity.type.base.annotations.klass.Class;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.world.vect3.IVect3;

import java.util.UUID;

@AutoEntityAttach
@Class(net.minecraft.world.entity.monster.spider.Spider.class)

@ApiStatus.Experimental

public class Spider extends Entity {
    public Spider(int id, EntityType type, IVect3 position, IVect3 velocity, UUID uuid, BaseComponent<?> name, net.minecraft.world.entity.Entity underlyingEntity) {
        super(id, type, position, velocity, uuid, name, underlyingEntity);
    }
}
