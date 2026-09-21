package pl.olafcio.avoid.net.entity.type.base;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.world.vect3.IVect3;

import java.util.UUID;

@ApiStatus.Internal
@FunctionalInterface
public interface NativeEntityConstructor<T extends Entity> {
    T construct(int id, EntityType type, IVect3 position, IVect3 velocity, UUID uuid, BaseComponent<?> name, net.minecraft.world.entity.Entity underlyingEntity);
}
