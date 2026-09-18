package pl.olafcio.avoid.net.entity.type;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.world.vect3.Vect3Native;

@Native
@ApiStatus.Internal
public final class EnderDragonNative {
    @ApiStatus.Internal
    private EnderDragonNative() {}

    public static <T extends net.minecraft.world.entity.boss.enderdragon.EnderDragon> EnderDragon convertFrom(T entity) {
        BaseComponent<?> name;

        try {
            name = COFromNative.from(entity.getName());
        } catch (Exception e) {
            name = null;
        }

        return new EnderDragon(
                entity.getId(),
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
