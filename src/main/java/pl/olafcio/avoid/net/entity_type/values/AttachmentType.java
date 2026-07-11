package pl.olafcio.avoid.net.entity_type.values;

import pl.olafcio.avoid.net.world.vect3.IVect3;
import pl.olafcio.avoid.net.world.vect3.Vect3;

import java.util.List;

public enum AttachmentType {
    PASSENGER(Fallback.AT_HEIGHT),
    VEHICLE(Fallback.AT_FEET),
    NAME_TAG(Fallback.AT_HEIGHT),
    WARDEN_CHEST(Fallback.AT_CENTER);

    public final Fallback fallback;

    AttachmentType(Fallback fallback) {
        this.fallback = fallback;
    }

    public interface Fallback {
        List<IVect3> ZERO = List.of(Vect3.ZERO);

        Fallback AT_FEET = (x, y) -> ZERO;
        Fallback AT_HEIGHT = (f, g) -> List.of(new Vect3(0.0, g, 0.0));
        Fallback AT_CENTER = (f, g) -> List.of(new Vect3(0.0, (double)g / 2.0, 0.0));

        List<IVect3> create(float var1, float var2);
    }
}
