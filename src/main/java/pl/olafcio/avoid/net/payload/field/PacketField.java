package pl.olafcio.avoid.net.payload.field;

import java.lang.annotation.*;

/**
 * Specifies the annotated field should be automatically read from and wrote to.<br/>
 * This also requires {@link Order @Order}. Also check out {@link pl.olafcio.avoid.net.payload.annotations.NoManual @NoManual}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PacketField {}
