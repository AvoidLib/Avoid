package pl.olafcio.avoid.net.packet;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public enum PacketSide {
    /**
     * Client-to-server (serverbound)
     * <br/><br/>
     * Client sends packets, server receives them.
     */
    C2S,

    /**
     * Server-to-client (clientbound)
     * <br/><br/>
     * Server sends packets, client receives them.
     */
    S2C,

    /**
     * Both C2S and S2C (universal)
     */
    BOTH
}
