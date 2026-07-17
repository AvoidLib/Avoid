package pl.olafcio.avoid.net.block.properties;

import java.lang.annotation.*;

/**
 * What to multiply the speed of players standing on this block with.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _speedFactor {
    float value();
}
