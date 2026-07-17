package pl.olafcio.avoid.net.block.properties;

import java.lang.annotation.*;

/**
 * The time required to break the block, in seconds.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _destroyTime {
    float value();
}
