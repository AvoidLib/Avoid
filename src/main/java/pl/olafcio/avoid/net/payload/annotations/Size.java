package pl.olafcio.avoid.net.payload.annotations;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.*;

/**
 * The size of the bytearray at the end of the custom payload.<br/>
 * Ignored when {@link NoManual @NoManual} is present.
 */
@ApiStatus.Experimental
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Size {
    int value();
}
