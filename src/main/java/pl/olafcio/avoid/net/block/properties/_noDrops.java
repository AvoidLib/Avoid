package pl.olafcio.avoid.net.block.properties;

import java.lang.annotation.*;

/**
 * Makes the block not drop anything.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _noDrops {}
