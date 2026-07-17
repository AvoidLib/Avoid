package pl.olafcio.avoid.net.block.properties;

import java.lang.annotation.*;

/**
 * The block's friction/slippyness.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _friction {
    float value();
}
