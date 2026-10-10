package pl.olafcio.avoid_impl.net.id;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.id.ID;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.OptionalID;

import java.util.Optional;

@Native
@ApiStatus.Internal
public final class IdentificationNative {
    @ApiStatus.Internal
    private IdentificationNative() {}

    public static Identifier convert(Identification id) {
        return Identifier.fromNamespaceAndPath(id.namespace(), id.path());
    }

    public static Identifier convert(ID id) {
        return Identifier.fromNamespaceAndPath(id.namespace(), id.path());
    }

    public static Identifier convert(OptionalID id) {
        if (id.namespace().isEmpty() && id.path().isEmpty())
            return null;

        return Identifier.fromNamespaceAndPath(id.namespace(), id.path());
    }

    public static Optional<Identifier> convertOptional(ID id) {
        return Optional.of(Identifier.fromNamespaceAndPath(id.namespace(), id.path()));
    }

    public static Optional<Identifier> convertOptional(OptionalID id) {
        if (id.namespace().isEmpty() && id.path().isEmpty())
            return Optional.empty();

        return Optional.of(Identifier.fromNamespaceAndPath(id.namespace(), id.path()));
    }

    public static Identifier convert(String id) {
        return Identifier.bySeparator(id, ':');
    }

    public static Identification convertFrom(Identifier id) {
        return new Identification(id.getNamespace(), id.getPath());
    }
}
