package pl.olafcio.avoid.net.entity.values;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.world.vect3.Vect3Native;

@Native
@ApiStatus.Internal
public final class DamageNative {
    @ApiStatus.Internal
    private DamageNative() {}

    public static Damage convert(DamageSource damageSource, RegistryAccess registryAccess) {
        return new Damage(
                IdentificationNative.convertFrom(registryAccess.lookupOrThrow(Registries.DAMAGE_TYPE)
                                                               .getKey(damageSource.type())),
                EntityNative.convertFromTry(damageSource.getEntity()),
                EntityNative.convertFromTry(damageSource.getDirectEntity()),
                Vect3Native.convert(damageSource.getSourcePosition())
        );
    }
}
