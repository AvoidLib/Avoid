package pl.olafcio.avoid.net.entity;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.Discouraged;

@Discouraged(reason = "This may be revamped")
@ApiStatus.Experimental
public final class EntityAccessor {
    @ApiStatus.Internal
    private EntityAccessor() {}

    public static net.minecraft.world.entity.Entity convert(Entity entity) {
        return entity.underlyingEntity;
    }
}
