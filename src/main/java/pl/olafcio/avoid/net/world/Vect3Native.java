package pl.olafcio.avoid.net.world;

import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class Vect3Native {
    @ApiStatus.Internal
    private Vect3Native() {}

    public static Vect3 convert(Vec3 vec3) {
        if (vec3.x == 0 && vec3.y == 0 && vec3.z == 0)
            return Vect3.ZERO;

        return new Vect3(vec3.x, vec3.y, vec3.z);
    }

    public static Vec3 convertFrom(Vect3 vec3) {
        if (vec3.x() == 0 && vec3.y() == 0 && vec3.z() == 0)
            return Vec3.ZERO;

        return new Vec3(vec3.x(), vec3.y(), vec3.z());
    }
}
