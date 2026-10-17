package pl.olafcio.avoid.net.services.list.session;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.NonExtendable
public interface ProfileProperty {
              String name();
              String value();
    @Nullable String signature();

    default boolean hasSignature() {
        return signature() != null;
    }
}
