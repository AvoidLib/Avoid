package pl.olafcio.avoid.net.payload;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.errors.NotImplementedException;

@ApiStatus.Experimental
public abstract class CustomPayload {
    /**
     * <b>Note:</b> This is not called if {@linkplain pl.olafcio.avoid.net.payload.annotations.NoManual @NoManual} is used.
     */
    public void read(byte[] data) {}

    /**
     * <b>Note:</b> This is not called if {@linkplain pl.olafcio.avoid.net.payload.annotations.NoManual @NoManual} is used.
     */
    public byte[] write() {
        return new byte[]{};
    }

    /**
     * Called after all the {@linkplain pl.olafcio.avoid.net.payload.field.PacketField packet fields} and the bytearray at the end of the packet are read.<br/>
     * Only called when the packet is being received.
     */
    public void onRead() {}

    /**
     * Called before all the {@linkplain pl.olafcio.avoid.net.payload.field.PacketField packet fields} and the bytearray at the end of the packet are written.<br/>
     * Only called when the packet is being sent.
     */
    public void onWrite() {}

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
