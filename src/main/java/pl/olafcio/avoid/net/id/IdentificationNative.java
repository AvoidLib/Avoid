package pl.olafcio.avoid.net.id;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class IdentificationNative {
    @ApiStatus.Internal
    private IdentificationNative() {}

    public static Identifier convert(Identification id) {
        return Identifier.fromNamespaceAndPath(id.namespace(), id.path());
    }

    public static Identifier convert(String id) {
        return Identifier.bySeparator(id, ':');
    }

    public static Identification convertFrom(Identifier id) {
        return new Identification(id.getNamespace(), id.getPath());
    }
}
