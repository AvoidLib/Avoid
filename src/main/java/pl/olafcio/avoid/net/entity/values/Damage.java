package pl.olafcio.avoid.net.entity.values;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.world.vect3.IVect3;

@ApiStatus.Experimental
public record Damage(
        Identification source,
        @Nullable Entity causingEntity,
        @Nullable Entity directEntity,
        @Nullable IVect3 damageSourcePosition
) {
    public Damage(Identification source) {
        this(source, null, null, null);
    }
}
