package pl.olafcio.avoid.net.entity.type;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.world.vect3.Vect3Native;

@Native
@ApiStatus.Internal
public final class PrimedTNTNative {
    @ApiStatus.Internal
    private PrimedTNTNative() {}

    public static <T extends net.minecraft.world.entity.item.PrimedTnt> PrimedTNT convertFrom(T entity) {
        BaseComponent<?> name;

        try {
            name = COFromNative.from(entity.getName());
        } catch (Exception e) {
            name = null;
        }

        return new PrimedTNT(
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
