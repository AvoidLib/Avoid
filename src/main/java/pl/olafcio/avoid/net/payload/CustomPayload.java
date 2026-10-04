package pl.olafcio.avoid.net.payload;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.errors.NotImplementedException;

@ApiStatus.Experimental
public abstract class CustomPayload {
    public abstract void read(byte[] data);
    public abstract byte[] write();

    /**
     * Handles the packet on the client.<br/>
     * Cannot be used on C2S (serverbound) packets.
     */
    public void acceptClient() {
        throw new NotImplementedException("'acceptClient' method not implemented on custom-payload: '%s'".formatted(this.getClass().getName()));
    }

    /**
     * Handles the packet on the server.<br/>
     * Cannot be used on S2C (clientbound) packets.
     */
    public void acceptServer() {
        throw new NotImplementedException("'acceptServer' method not implemented on custom-payload: '%s'".formatted(this.getClass().getName()));
    }
}
