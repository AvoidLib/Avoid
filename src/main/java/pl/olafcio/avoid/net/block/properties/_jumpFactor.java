package pl.olafcio.avoid.net.block.properties;

import java.lang.annotation.*;

/**
 * What to multiply the jump strength of players standing on this block with.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _jumpFactor {
    float value();
}
