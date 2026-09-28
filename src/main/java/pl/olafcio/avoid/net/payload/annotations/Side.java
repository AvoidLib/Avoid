package pl.olafcio.avoid.net.payload.annotations;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.packet.PacketSide;

import java.lang.annotation.*;

@ApiStatus.Experimental
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Side {
    PacketSide value();
}
