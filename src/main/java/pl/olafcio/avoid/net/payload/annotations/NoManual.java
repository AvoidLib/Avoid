package pl.olafcio.avoid.net.payload.annotations;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.packet.PacketSide;
import pl.olafcio.avoid.net.payload.CustomPayload;

import java.lang.annotation.*;

/**
 * Removes the byte array at the end of the custom payload.<br/>
 * This causes the {@link CustomPayload#read(byte[]) CustomPayload.read(byte[] data)} and {@link CustomPayload#write()} functions to not be called.
 */
@ApiStatus.Experimental
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoManual {}
