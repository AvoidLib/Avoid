package pl.olafcio.avoid.net.payload;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.id.Identification;

import java.util.function.Supplier;

@ApiStatus.Experimental
public final class Networking {
    @ApiStatus.Internal
    private Networking() {}

    public static <T extends CustomPayload> void register(Identification id, Supplier<T> payload, Class<T> clazz) {
        pl.olafcio.avoid_impl.net.payload.Networking.register(id, payload, clazz);
    }
}
