package pl.olafcio.avoid.net.client.event;

import org.jetbrains.annotations.ApiStatus;

@SuppressWarnings("InstantiationOfUtilityClass")
@ApiStatus.Experimental
public final class ClientTickEvent {
    public static final ClientTickEvent INSTANCE
                  = new ClientTickEvent();

    private ClientTickEvent() {}
}
