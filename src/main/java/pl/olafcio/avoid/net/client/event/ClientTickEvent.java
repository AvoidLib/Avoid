package pl.olafcio.avoid.net.client.event;

import org.jetbrains.annotations.ApiStatus;

/**
 * Fired when a tick passes on the client.
 * <br/><br/>
 * A tick is a {@code 1/20} of a second. In other words, a second consists of 20 ticks.<br/>
 * A tick is used by the game for e.g. processing redstone.
 */
@SuppressWarnings("InstantiationOfUtilityClass")
@ApiStatus.Experimental
public final class ClientTickEvent {
    public static final ClientTickEvent INSTANCE
                  = new ClientTickEvent();

    private ClientTickEvent() {}
}
