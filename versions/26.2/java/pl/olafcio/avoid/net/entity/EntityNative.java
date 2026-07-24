package pl.olafcio.avoid.net.entity;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.entity.custom_internal.IAvoidEntity;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.world.vect3.Vect3Native;

@Native
@ApiStatus.Internal
public final class EntityNative {
    @ApiStatus.Internal
    private EntityNative() {}

    public static net.minecraft.world.entity.Entity convert(Entity entity) {
        return entity.underlyingEntity;
    }

    public static Entity convertFrom(net.minecraft.world.entity.Entity entity) {
        return convertFrom(entity, null);
    }

    public static Entity convertFromTry(net.minecraft.world.entity.Entity entity) {
        int id;

        try                 { id = entity.getId(); }
        catch (Exception e) { id = 0;              }

        if (entity instanceof Player player)
            return PlayerNative.convertFrom(player, id);
        else if (entity instanceof IAvoidEntity wrapper)
            return wrapper.getAvoidEntity();

        BaseComponent<?> name;

        try {
            name = COFromNative.from(entity.getName());
        } catch (Exception e) {
            name = null;
        }

        return new Entity(
                id,
                EntityTypeNative.convertFrom(entity.getType()),
                Vect3Native.convert(entity.position()),
                Vect3Native.convert(entity.getDeltaMovement()),
                entity.getUUID(),
                entity.getStringUUID(),
                name,
                entity
        ) {};
    }

    public static Entity convertFrom(net.minecraft.world.entity.Entity entity, Integer idOverride) {
        if (entity instanceof Player player)
            return PlayerNative.convertFrom(player, idOverride);
        else if (entity instanceof IAvoidEntity wrapper)
            return wrapper.getAvoidEntity();

        BaseComponent<?> name;

        try {
            name = COFromNative.from(entity.getName());
        } catch (Exception e) {
            name = null;
        }

        return new Entity(
                idOverride != null
                        ? idOverride
                        : entity.getId(),
                EntityTypeNative.convertFrom(entity.getType()),
                Vect3Native.convert(entity.position()),
                Vect3Native.convert(entity.getDeltaMovement()),
                entity.getUUID(),
                entity.getStringUUID(),
                name,
                entity
        ) {};
    }
}
