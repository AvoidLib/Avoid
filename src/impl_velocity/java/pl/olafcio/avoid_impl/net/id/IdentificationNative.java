package pl.olafcio.avoid_impl.net.id;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.id.Identification;

@Native
@ApiStatus.Internal
public final class IdentificationNative {
    @ApiStatus.Internal
    private IdentificationNative() {}

    public static Key convert(Identification id) {
        return Key.key(id.namespace(), id.path());
    }

    public static Identification convertFrom(Key id) {
        return new Identification(id.namespace(), id.value());
    }
}
