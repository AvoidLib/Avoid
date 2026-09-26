package pl.olafcio.avoid.net.entity_type;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid_impl.net.entity.custom_internal.EntityConstructor;
import pl.olafcio.avoid.net.id.Identification;

@ApiStatus.Experimental
public final class EntityTypes {
    @ApiStatus.Internal
    private EntityTypes() {}

    /**
     * <b>NOTE:</b> Creating entity types on Paper may not work.
     */
    public static <T extends pl.olafcio.avoid.net.entity.Entity> void register(Identification entityTypeID, Class<? extends T> klass, EntityConstructor<T> constructor) {
        pl.olafcio.avoid_impl.net.entity_type.EntityTypes.register(entityTypeID, klass, constructor);
    }
}
