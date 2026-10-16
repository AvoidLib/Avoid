package pl.olafcio.avoid.net.payload;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.id.Identification;

import java.util.function.Supplier;

/**
 * A namespace used to manage custom payloads.
 * <br/><br/>
 * To register a custom payload, see: {@link #register(Identification, Supplier, Class) &lt;Payload extends CustomPayload&gt; register([Identification] id, Supplier&lt;Payload&gt; payload, Class&lt;Payload&gt; payload)}
 */
@ApiStatus.Experimental
public final class Networking {
    @ApiStatus.Internal
    private Networking() {}

    /**
     * Creates a new custom payload type.
     */
    public static <T extends CustomPayload> void register(Identification id, Supplier<T> payload, Class<T> clazz) {
        pl.olafcio.avoid_impl.net.payload.Networking.register(id, payload, clazz);
    }
}
